package jps.hyperspin.main.controller;

import java.io.File;

import jps.hyperspin.MainClass;
import jps.hyperspin.main.model.Systems;
import jps.hyperspin.main.model.VersionStatut;
import jps.hyperspin.module.dbdownloader.model.DatabaseDetail;
import jps.hyperspin.module.dbdownloader.view.DatabaseDetailPanel;
import jps.hyperspin.module.dbdownloader.view.SystemIniProperties;

public class MainController {

	public static MainController instance = new MainController();

	/**
	 * Put a new system.
	 * 
	 * @param system
	 * @param statut
	 */
	public void changeSystemStatut(String system, VersionStatut statut) {
		Systems.instance.put(system, statut);
		MainClass.mainFrame.getSystemListPanel().repaint();

	}

	/**
	 * Compute the system list.
	 * 
	 * @return th number of system foudn in user settings.
	 */
	public int computeSystems() {

		System.out.println("Hyper spin field  edited");
		// Update JList

		File file = new File(MainClass.HYPERSPIN_PATH);
		if (file.isDirectory()) {
			File settings = new File(MainClass.HYPERSPIN_PATH + "/settings");
			if (settings.isDirectory()) {
				for (String s : settings.list()) {
					if (s.endsWith(".ini")) {
						String ini = s.substring(0, s.length() - 4);
						if (!ini.equals("Main Menu") && !ini.equals("Settings")) {
							Systems.instance.put(ini, null);
						}
					}
				}
			}
		}

		return Systems.instance.list().size();
	}

	public DatabaseDetail getDbDetail() {
		DatabaseDetailPanel detailPanel = MainClass.mainFrame.getMainTabbedPane().getDbDownloaderTab()
				.getDatabaseDetailPanel();
		DatabaseDetail detail = new DatabaseDetail();
		detail.mediaDir = detailPanel.getMediaDirField().getText();
		detail.userDatabaseDir = detailPanel.getUserDatabaseDirField().getText();
		detail.systemIniProperties = new SystemIniProperties(MainClass.HYPERSPIN_PATH,
				MainClass.mainFrame.getSystemSelected());
		return detail;

	}

}