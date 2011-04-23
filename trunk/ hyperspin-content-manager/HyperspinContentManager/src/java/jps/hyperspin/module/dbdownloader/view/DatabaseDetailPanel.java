package jps.hyperspin.module.dbdownloader.view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import jps.hyperspin.MainClass;
import jps.hyperspin.common.DatabaseUtilities;
import jps.hyperspin.common.i18n.Message;
import jps.hyperspin.common.presentation.BasicProgressDialog;
import jps.hyperspin.common.presentation.LayoutUtilities;
import jps.hyperspin.main.model.VersionStatut;
import jps.hyperspin.module.dbdownloader.model.MenuType;
import jps.hyperspin.module.dbdownloader.worker.CheckDatabaseVersionWorker;
import jps.hyperspin.module.dbdownloader.worker.DbDowloaderWorker;

/**
 * This panel display the detailed information of the selected system. It
 * poroses also to upload database if a newer version is available.
 * 
 * @author JPS
 * 
 */
public class DatabaseDetailPanel extends JPanel implements IDatabaseDetail,
		ActionListener {

	/**
	 * 
	 */
	private JLabel userDatabaseDirLabel = new JLabel(
			Message.getMessage("dbdownloader.user.database.dir.label"));

	/**
	 * 
	 */
	private JTextField userDatabaseDirField = new JTextField();

	/**
	 *  
	 */
	private JLabel mediaRepositoryLabel = new JLabel(
			Message.getMessage("dbdownloader.media.dir.label"));

	/**
	 *  
	 */
	private JTextField mediaRepositoryField = new JTextField();

	/**
	 *  
	 */
	private JLabel romsPathLabel = new JLabel(
			Message.getMessage("dbdownloader.rom.dir.label"));

	/**
	 *  
	 */
	private JTextField romsPathField = new JTextField();

	/**
	 *  
	 */
	private JLabel userVersionLabel = new JLabel(
			Message.getMessage("dbdownloader.user.version"));

	/**
	 *  
	 */
	private JTextField userVersion = new JTextField();

	/**
	 *  
	 */
	private JLabel downloadedVersionLabel = new JLabel(
			Message.getMessage("dbdownloader.downoader.version"));

	/**
	 *  
	 */
	private JTextField downloadedVersion = new JTextField();
	/**
	 *  
	 */
	private JLabel hyperlistVersionLabel = new JLabel(
			Message.getMessage("dbdownloader.hyperlist.version"));

	/**
	 *  
	 */
	private JTextField hyperlistVersion = new JTextField();

	/**
	 *  
	 */
	private JButton updateNow = new JButton(
			Message.getMessage("dbdownloader.upload.database.label"));

	/**
	 *  
	 */
	private JTextArea versionNoteArea = new JTextArea();;

	/**
	 *  
	 */
	private SystemIniProperties iniProp;

	/**
	 * Tab parent
	 */
	private DatabaseTab databaseTab;

	/**
	 * 
	 */
	public DatabaseDetailPanel(DatabaseTab databaseTab) {

		setBackground(Color.white);

		this.databaseTab = databaseTab;

		// Layout
		// Labels
		this.setLayout(LayoutUtilities.newLayout());
		GridBagConstraints c = LayoutUtilities.newConstraint(0, 0);

		this.add(userDatabaseDirLabel, c);

		c.gridy++;
		this.add(mediaRepositoryLabel, c);

		c.gridy++;
		this.add(romsPathLabel, c);

		c.gridy++;
		this.add(userVersionLabel, c);

		c.gridy++;
		c.insets = new Insets(30, 0, 0, 0);
		this.add(hyperlistVersionLabel, c);
		c.insets = LayoutUtilities.defaultInsets();

		c.gridy++;
		this.add(downloadedVersionLabel, c);

		LayoutUtilities.fixSize(userDatabaseDirLabel, downloadedVersionLabel);
		LayoutUtilities.fixSize(romsPathLabel, downloadedVersionLabel);
		LayoutUtilities.fixSize(mediaRepositoryLabel, downloadedVersionLabel);
		LayoutUtilities.fixSize(hyperlistVersionLabel, downloadedVersionLabel);
		LayoutUtilities.fixSize(userVersionLabel, downloadedVersionLabel);

		// Fields
		c = LayoutUtilities.newConstraint(1, 0);

		this.add(userDatabaseDirField, c);
		userDatabaseDirField.setColumns(40);
		userDatabaseDirField.setEnabled(false);

		c.gridy++;
		this.add(mediaRepositoryField, c);
		mediaRepositoryField.setEnabled(false);
		LayoutUtilities.fixSize(mediaRepositoryField, userDatabaseDirField);

		c.gridy++;
		this.add(romsPathField, c);
		romsPathField.setEnabled(false);
		LayoutUtilities.fixSize(romsPathField, userDatabaseDirField);

		c.gridy++;
		this.add(userVersion, c);
		userVersion.setEnabled(false);
		LayoutUtilities.fixSize(userVersion, userDatabaseDirField);
		c.insets = new Insets(30, 0, 0, 0);

		c.gridy++;
		this.add(hyperlistVersion, c);
		hyperlistVersion.setEnabled(false);
		LayoutUtilities.fixSize(hyperlistVersion, userDatabaseDirField);
		c.insets = LayoutUtilities.defaultInsets();

		c.gridy++;
		this.add(downloadedVersion, c);
		downloadedVersion.setEnabled(false);
		LayoutUtilities.fixSize(downloadedVersion, userDatabaseDirField);

		c.gridy++;
		c.anchor = GridBagConstraints.LINE_END;
		this.add(updateNow, c);
		updateNow.setEnabled(false);
		LayoutUtilities.fixSize(updateNow, userDatabaseDirField);
		updateNow.addActionListener(this);
		updateNow.setEnabled(false);

		// Note panel
		JPanel note = new JPanel();
		note.setBorder(BorderFactory.createTitledBorder(Message
				.getMessage("dbdownloader.note.title")));
		note.setBackground(Color.WHITE);
		versionNoteArea.setLineWrap(true);
		versionNoteArea.setEditable(false);
		versionNoteArea.setColumns(35);
		note.add(versionNoteArea);
		LayoutUtilities.fixSize(note, 500, 80);
		c.insets = new Insets(30, 0, 0, 0);
		c.gridx = 0;
		c.gridy++;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.CENTER;
		this.add(note, c);

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == updateNow) {

			// Process
			new BasicProgressDialog(new DbDowloaderWorker());

			// Update filed
			updateFields();
		}

	}

	/**
	 * 
	 */
	public void updateFields() {
		try {
			String system = MainClass.mainFrame.getSystemSelected();
			if (system == null) {
				throw new IllegalArgumentException();
			}

			// ini
			iniProp = new SystemIniProperties(
					MainClass.mainFrame.getHyperSpinPath(),
					databaseTab.getSystemSelected());

			System.out.println("Ini file combo box change");
			// Xml
			String selected = system;
			userDatabaseDirField.setText(DatabaseUtilities
					.getUserDatabasePath());
			mediaRepositoryField.setText(MainClass.mainFrame.getHyperSpinPath()
					+ "/Media/" + selected);

			romsPathField.setText(getIniProp().getRomPath());

			// worker
			CheckDatabaseVersionWorker worker = new CheckDatabaseVersionWorker(
					MainClass.mainFrame.getSystemSelected()) {

				/* (non-Javadoc)
				 * @see jps.hyperspin.common.worker.CommonWorker#done()
				 */
				@Override
				public void done() {
					// Update fields
					downloadedVersion.setText(getVersion(downloadedDatabase));
					hyperlistVersion.setText(getVersion(hyperlistDatabase));
					userVersion.setText(getVersion(userDatabase));

					// Enable update button
					updateNow.setEnabled(true);
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
					versionNoteArea.setText(msg);

					super.done();
				}

			};
			new BasicProgressDialog(worker);

		} catch (Throwable e) {
			MainClass.mainFrame.getLogger().info("Erreur : " + e);
			userDatabaseDirField.setText("");
			mediaRepositoryField.setText("");
			romsPathField.setText("");
			hyperlistVersion.setText("");
			downloadedVersion.setText("");
			userVersion.setText("");
			versionNoteArea.setText("");
			updateNow.setEnabled(false);

		}
	}

	private String getVersion(MenuType db) {
		if (db == null) {
			return Message.getMessage("dbdownloader.db.not.found.msg");
		} else if (db.getHeader() == null) {
			return Message.getMessage("dbdownloader.db.noversion.msg");
		}
		return db.getHeader().getListversion() + " - "
				+ db.getHeader().getLastlistupdate();
	}

	/**
	 * @return the mediaRepositoryField
	 */
	public final String getMediaRepository() {
		return mediaRepositoryField.getText();
	}

	public final String getUserDatabaseDir() {
		return userDatabaseDirField.getText();
	}

	public final String getIniSelected() {
		return databaseTab.getSystemSelected().toString();
	}

	public final SystemIniProperties getIniProp() {
		return iniProp;
	}

}
