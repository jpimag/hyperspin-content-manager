package jps.hyperspin.module.dbdownloader.presentation;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.jps.hyperspin.module.dbdownloader.model.MenuType;
import java.jps.hyperspin.module.dbdownloader.worker.CheckDatabaseVersionWorker;
import java.jps.hyperspin.module.dbdownloader.worker.DbDowloaderWorker;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


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
	private JLabel userVersionLabel = new JLabel("Your DB Version");

	/**
	 *  
	 */
	private JTextField userVersion = new JTextField();

	/**
	 *  
	 */
	private JLabel downloadedVersionLabel = new JLabel("Downloaded DB Version");

	/**
	 *  
	 */
	private JTextField downloadedVersion = new JTextField();
	/**
	 *  
	 */
	private JLabel hyperlistVersionLabel = new JLabel("HyperList DB Version");

	/**
	 *  
	 */
	private JTextField hyperlistVersion = new JTextField();

	/**
	 *  
	 */
	private JButton updateNow = new JButton("Download from HyperList");

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
		this.add(userVersionLabel, c);
		c.insets = LayoutUtilities.defaultInsets();

		c.gridy++;
		this.add(hyperlistVersionLabel, c);

		c.gridy++;
		this.add(downloadedVersionLabel, c);

		LayoutUtilities.fixSize(generatedDatabaseDirLabel,
				downloadedVersionLabel);
		LayoutUtilities.fixSize(romsPathLabel, downloadedVersionLabel);
		LayoutUtilities.fixSize(mediaRepositoryLabel, downloadedVersionLabel);
		LayoutUtilities.fixSize(hyperlistVersionLabel, downloadedVersionLabel);
		LayoutUtilities.fixSize(userVersionLabel, downloadedVersionLabel);

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

		c.insets = new Insets(30, 0, 0, 0);

		c.gridy++;
		this.add(userVersion, c);
		userVersion.setEnabled(false);
		LayoutUtilities.fixSize(userVersion, generatedDatabaseDirField);
		c.insets = LayoutUtilities.defaultInsets();

		c.gridy++;
		this.add(hyperlistVersion, c);
		hyperlistVersion.setEnabled(false);
		LayoutUtilities.fixSize(hyperlistVersion, generatedDatabaseDirField);

		c.gridy++;
		this.add(downloadedVersion, c);
		downloadedVersion.setEnabled(false);
		LayoutUtilities.fixSize(downloadedVersion, generatedDatabaseDirField);

		c.gridy++;
		c.anchor = GridBagConstraints.LINE_END;
		this.add(updateNow, c);
		updateNow.setEnabled(false);
		LayoutUtilities.fixSize(updateNow, generatedDatabaseDirField);
		updateNow.addActionListener(this);
		updateNow.setEnabled(false);
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

				// worker
				CheckDatabaseVersionWorker worker = new CheckDatabaseVersionWorker() {

					/* (non-Javadoc)
					 * @see jps.hyperspin.common.worker.CommonWorker#done()
					 */
					@Override
					public void done() {
						// Update fields
						if (getCurrentDatabase() != null
								&& getCurrentDatabase().getHeader() != null) {
							downloadedVersion
									.setText(getVersion(getCurrentDatabase()));
						} else {
							downloadedVersion.setText("Unknown");
						}
						if (getLastDatabase() != null
								&& getLastDatabase().getHeader() != null) {
							hyperlistVersion
									.setText(getVersion(getLastDatabase()));
						} else {
							hyperlistVersion.setText("Unknown");
						}

						// Enable update button
						updateNow.setEnabled(true);

						super.done();
					}

				};
				new BasicProgressDialog(worker);

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

			// Process
			new BasicProgressDialog(new DbDowloaderWorker());

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
