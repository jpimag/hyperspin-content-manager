package jps.hyperspin.module.dbdownloader.controller;

import java.io.File;

import jps.hyperspin.MainClass;
import jps.hyperspin.common.DatabaseUtilities;
import jps.hyperspin.common.i18n.Message;
import jps.hyperspin.common.view.BasicProgressDialog;
import jps.hyperspin.main.controller.CommonLogger;
import jps.hyperspin.main.model.VersionStatut;
import jps.hyperspin.module.dbdownloader.model.DatabaseDetail;
import jps.hyperspin.module.dbdownloader.model.generated.menu.MenuType;
import jps.hyperspin.module.dbdownloader.view.DatabaseDetailPanel;
import jps.hyperspin.module.dbdownloader.view.SystemIniProperties;
import jps.hyperspin.module.dbdownloader.worker.CheckDatabaseVersionWorker;

public class DbDownLoaderController {

	public static DbDownLoaderController instance = new DbDownLoaderController();

	public DatabaseDetail getDbDetail() {
		DatabaseDetailPanel detailPanel = MainClass.mainFrame
				.getMainTabbedPane().getDatabaseTab().getDatabaseDetailPanel();
		DatabaseDetail detail = new DatabaseDetail();
		detail.mediaDir = detailPanel.getMediaDirField().getText();
		detail.userDatabaseDir = detailPanel.getUserDatabaseDirField()
				.getText();
		detail.systemIniProperties = new SystemIniProperties(
				MainClass.HYPERSPIN_PATH,
				MainClass.mainFrame.getSystemSelected());
		return detail;

	}

	/**
	 * 
	 */
	public void updateDetails() {
		final DatabaseDetailPanel detailPanel = MainClass.mainFrame
				.getMainTabbedPane().getDatabaseTab().getDatabaseDetailPanel();

		try {
			String system = MainClass.mainFrame.getSystemSelected();
			if (system == null) {
				throw new IllegalArgumentException();
			}
			// Ini file
			SystemIniProperties iniProp = new SystemIniProperties(
					MainClass.HYPERSPIN_PATH, system);

			System.out.println("Ini file combo box change");
			// Xml
			String selected = system;
			detailPanel.getUserDatabaseDirField().setText(
					DatabaseUtilities.getUserDatabasePath());
			detailPanel.getMediaDirField().setText(
					MainClass.HYPERSPIN_PATH + File.separator + "Media"
							+ File.separator + selected);

			detailPanel.getRomsPathField().setText(iniProp.getRomPath());

			// worker
			CheckDatabaseVersionWorker worker = new CheckDatabaseVersionWorker(
					MainClass.mainFrame.getSystemSelected()) {

				@Override
				public void done() {
					// Update fields
					detailPanel.getDownloadedVersionField().setText(
							getVersion(downloadedDatabase));
					detailPanel.getHyperlistVersionField().setText(
							getVersion(hyperlistDatabase));
					detailPanel.getUserVersionField().setText(
							getVersion(userDatabase));

					// Enable update button
					detailPanel.getDownloadButton().setEnabled(true);
					String msg;
					// Set Tip Message according to versions
					VersionStatut statut = getVersionStatut();

					switch (statut) {
					case SYSTEM_NOT_AVAILABLE:
						msg = Message
								.getMessage("dbdownloader.hyperlist.notfound.msg");
						break;
					case SYSTEM_NOT_VERSIONNED:
						msg = Message
								.getMessage("dbdownloader.hyperlist.notversionned.msg");
						break;
					case OUT_DATED_DOWNLOADED_DB:
						msg = Message
								.getMessage("dbdownloader.download.db.old.msg");
						break;
					case OUT_DATED_USER_DB:
						msg = Message
								.getMessage("dbdownloader.user.db.old.msg");
						break;
					case UP_TO_DATE:
					default:
						msg = Message.getMessage("dbdownloader.uptodate.msg");
					}
					detailPanel.getNoteLabel().setText(msg);

					super.done();
				}

			};
			new BasicProgressDialog(worker);

		} catch (Throwable e) {
			CommonLogger.instance.info("Erreur : " + e);
			detailPanel.getUserDatabaseDirField().setText("");
			detailPanel.getMediaDirField().setText("");
			detailPanel.getRomsPathField().setText("");
			detailPanel.getHyperlistVersionField().setText("");
			detailPanel.getDownloadedVersionField().setText("");
			detailPanel.getUserVersionField().setText("");
			detailPanel.getNoteLabel().setText("");
			detailPanel.getDownloadedVersionField().setEnabled(false);

		}
	}

	/**
	 * 
	 * @param db
	 * @return
	 */
	private String getVersion(MenuType db) {
		if (db == null) {
			return Message.getMessage("dbdownloader.db.not.found.msg");
		} else if (db.getHeader() == null) {
			return Message.getMessage("dbdownloader.db.noversion.msg");
		}
		return db.getHeader().getListversion() + " - "
				+ db.getHeader().getLastlistupdate();
	}

}
