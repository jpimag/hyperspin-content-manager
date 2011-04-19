package jps.hyperspin.module.dbdownloader.presentation;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import jps.hyperspin.MainClass;
import jps.hyperspin.common.DatabaseUtilities;
import jps.hyperspin.common.FileUtilities;
import jps.hyperspin.common.LayoutUtilities;
import jps.hyperspin.exception.HCMDatabaseException;
import jps.hyperspin.module.dbdownloader.model.MenuType;
import jps.hyperspin.module.dbdownloader.process.DownloadProcessor;
import jps.hyperspin.module.dbdownloader.process.SystemIniProperties;

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
	private JLabel generatedDatabaseDirLabel = new JLabel(
			"Current database dir ");

	/**
	 * 
	 */
	private JTextField generatedDatabaseDirField = new JTextField();

	/**
	 *  
	 */
	private JLabel mediaRepositoryLabel = new JLabel("Media repository ");

	/**
	 *  
	 */
	private JTextField mediaRepositoryField = new JTextField();

	/**
	 *  
	 */
	private JLabel romsPathLabel = new JLabel("Roms path ");

	/**
	 *  
	 */
	private JTextField romsPathField = new JTextField();

	/**
	 *  
	 */
	private JLabel currentVersionLabel = new JLabel(
			"Reference Database Version");

	/**
	 *  
	 */
	private JTextField currentVersion = new JTextField();
	/**
	 *  
	 */
	private JLabel lastVersionLabel = new JLabel("Last Version Available");

	/**
	 *  
	 */
	private JTextField lastVersion = new JTextField();

	/**
	 *  
	 */
	private JButton updateNow = new JButton("Update Now");

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

		this.add(generatedDatabaseDirLabel, c);

		c.gridy++;
		this.add(mediaRepositoryLabel, c);
		c.gridy++;
		this.add(romsPathLabel, c);

		c.gridy++;
		c.insets = new Insets(30, 0, 0, 0);
		this.add(currentVersionLabel, c);

		c.gridy++;
		c.insets = LayoutUtilities.defaultInsets();
		this.add(lastVersionLabel, c);

		LayoutUtilities.fixSize(generatedDatabaseDirLabel, currentVersionLabel);
		LayoutUtilities.fixSize(romsPathLabel, currentVersionLabel);
		LayoutUtilities.fixSize(mediaRepositoryLabel, currentVersionLabel);
		LayoutUtilities.fixSize(lastVersionLabel, currentVersionLabel);

		// Fields
		c = LayoutUtilities.newConstraint(1, 0);

		this.add(generatedDatabaseDirField, c);
		generatedDatabaseDirField.setColumns(40);
		generatedDatabaseDirField.setEnabled(false);

		c.gridy++;
		this.add(mediaRepositoryField, c);
		mediaRepositoryField.setEnabled(false);
		LayoutUtilities
				.fixSize(mediaRepositoryField, generatedDatabaseDirField);

		c.gridy++;
		this.add(romsPathField, c);
		romsPathField.setEnabled(false);
		LayoutUtilities.fixSize(romsPathField, generatedDatabaseDirField);

		c.gridy++;
		c.insets = new Insets(30, 0, 0, 0);
		this.add(currentVersion, c);
		currentVersion.setEnabled(false);
		LayoutUtilities.fixSize(currentVersion, generatedDatabaseDirField);
		c.insets = LayoutUtilities.defaultInsets();

		c.gridy++;
		this.add(lastVersion, c);
		lastVersion.setEnabled(false);
		LayoutUtilities.fixSize(lastVersion, generatedDatabaseDirField);

		c.gridy++;
		c.anchor = GridBagConstraints.LINE_END;
		this.add(updateNow, c);
		updateNow.setEnabled(false);
		LayoutUtilities.fixSize(updateNow, generatedDatabaseDirField);
		updateNow.addActionListener(this);

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public void updateFields() {
		try {
			String system = MainClass.mainFrame.getSystemSelected();
			if (system != null) {
				// Processor
				DownloadProcessor writeDbProcessor = new DownloadProcessor(this);

				// ini
				iniProp = new SystemIniProperties(
						MainClass.mainFrame.getHyperSpinPath(),
						databaseTab.getSystemSelected());

				System.out.println("Ini file combo box change");
				// Xml
				String selected = system;
				generatedDatabaseDirField.setText(MainClass.mainFrame
						.getHyperSpinPath() + "/Databases/" + selected);
				mediaRepositoryField.setText(MainClass.mainFrame
						.getHyperSpinPath() + "/Media/" + selected);

				romsPathField.setText(getIniProp().getRomPath());

				// Database version
				try {
					MenuType db = DatabaseUtilities.loadDatabase(
							DatabaseUtilities.getReferenceDatabasePath(),
							MainClass.mainFrame.getLogger());
					if (db.getHeader() != null) {
						currentVersion.setText(getVersion(db));
					} else {
						currentVersion.setText("Unknown");
					}
				} catch (FileNotFoundException e) {
					currentVersion.setText("Unknown");
				}

				// Check DB from hyperlist web site
				lastVersion.setText(getVersion(writeDbProcessor
						.getLastAvailableDbs(false).get(
								MainClass.mainFrame.getSystemSelected())));

				// Update button
				updateNow.setEnabled(true);

			} else {
				generatedDatabaseDirField.setText("");
				mediaRepositoryField.setText("");
				romsPathField.setText("");

			}
		} catch (HCMDatabaseException e) {
			MainClass.mainFrame.getLogger().info("Erreur : " + e);
		}
	}

	private String getVersion(MenuType db) {
		return db.getHeader().getListversion() + " - "
				+ db.getHeader().getLastlistupdate();
	}

	/**
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == updateNow) {
			// Processor
			DownloadProcessor downloadProcessor = new DownloadProcessor(this);

			// Getdatabases from HyperList site
			Map<String, MenuType> map = downloadProcessor
					.getLastAvailableDbs(true);

			// Delete all existing files
			FileUtilities.deleteAllFiles(
					DatabaseUtilities.getReferenceDatabaseDir(), "xml");

			// Write databases from HyperList site
			for (String genre : map.keySet()) {
				DatabaseUtilities.writeDatabase(map.get(genre),
						DatabaseUtilities.getGenreDatabasePath(genre));
			}

			// Update filed
			updateFields();
		}

	}

	/**
	 * @return the mediaRepositoryField
	 */
	public final String getMediaRepository() {
		return mediaRepositoryField.getText();
	}

	public final String getGeneratedDatabaseDir() {
		return generatedDatabaseDirField.getText();
	}

	public final String getIniSelected() {
		return databaseTab.getSystemSelected().toString();
	}

	public final SystemIniProperties getIniProp() {
		return iniProp;
	}

}
