package jps.hyperspin.module.dbdownloader.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import jps.hyperspin.common.i18n.Message;
import jps.hyperspin.common.view.BasicProgressDialog;
import jps.hyperspin.module.dbdownloader.controller.DbDownLoaderController;
import jps.hyperspin.module.dbdownloader.worker.DbDowloaderWorker;

public class DatabaseDetailPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField userDatabaseDirField = null; // @jve:decl-index=0:visual-constraint="256,45"
	private JLabel mediaDirLabel = null;
	private JTextField mediaDirField = null;
	private JLabel romsPathLabel = null;
	private JTextField romsPathField = null;
	private JLabel userDatabaseDirLabel = null;
	private JLabel userVersionLabel = null;
	private JTextField userVersionField = null;
	private JLabel downloadedVersionLabel = null;
	private JTextField downloadedVersionField = null;
	private JLabel hyperlistVersionLabel = null;
	private JTextField hyperlistVersionField = null;
	private JButton downloadButton = null;
	private JLabel noteLabel = null;

	/**
	 * @return the noteLabel
	 */
	public JLabel getNoteLabel() {
		return noteLabel;
	}

	/**
	 * This is the default constructor
	 */
	public DatabaseDetailPanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		GridBagConstraints gridBagConstraints30 = new GridBagConstraints();
		gridBagConstraints30.gridx = 6;
		gridBagConstraints30.gridy = 9;
		GridBagConstraints gridBagConstraints27 = new GridBagConstraints();
		gridBagConstraints27.gridx = 0;
		gridBagConstraints27.gridy = 0;
		GridBagConstraints gridBagConstraints26 = new GridBagConstraints();
		gridBagConstraints26.gridx = 6;
		gridBagConstraints26.gridy = 9;
		GridBagConstraints gridBagConstraints25 = new GridBagConstraints();
		gridBagConstraints25.gridx = 6;
		gridBagConstraints25.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints25.gridy = 9;
		GridBagConstraints gridBagConstraints24 = new GridBagConstraints();
		gridBagConstraints24.gridx = 6;
		gridBagConstraints24.gridy = 12;
		GridBagConstraints gridBagConstraints23 = new GridBagConstraints();
		gridBagConstraints23.gridx = 6;
		gridBagConstraints23.gridy = 9;
		GridBagConstraints gridBagConstraints22 = new GridBagConstraints();
		gridBagConstraints22.gridx = 5;
		gridBagConstraints22.gridheight = 2;
		gridBagConstraints22.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints22.insets = new Insets(30, 0, 0, 0);
		gridBagConstraints22.gridwidth = 2;
		gridBagConstraints22.gridy = 12;
		noteLabel = new JLabel();
		noteLabel.setText("");
		noteLabel.setBorder(BorderFactory.createTitledBorder(Message
				.getMessage("dbdownloader.note.title")));
		noteLabel.setPreferredSize(new Dimension(300, 100));
		noteLabel.setHorizontalAlignment(SwingConstants.CENTER);
		noteLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		noteLabel.setBackground(Color.WHITE);
		GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
		gridBagConstraints21.gridx = 6;
		gridBagConstraints21.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints21.gridy = 9;
		GridBagConstraints gridBagConstraints19 = new GridBagConstraints();
		gridBagConstraints19.gridx = 6;
		gridBagConstraints19.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints19.gridy = 9;
		GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
		gridBagConstraints15.gridx = 6;
		gridBagConstraints15.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints15.gridy = 9;
		GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
		gridBagConstraints13.fill = GridBagConstraints.BOTH;
		gridBagConstraints13.gridy = 8;
		gridBagConstraints13.weightx = 1.0;
		gridBagConstraints13.gridx = 6;
		GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
		gridBagConstraints12.gridx = 5;
		gridBagConstraints12.insets = new Insets(0, 0, 0, 20);
		gridBagConstraints12.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints12.gridy = 8;
		hyperlistVersionLabel = new JLabel();
		hyperlistVersionLabel.setText(Message
				.getMessage("dbdownloader.hyperlist.version"));
		hyperlistVersionLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		hyperlistVersionLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
		gridBagConstraints11.fill = GridBagConstraints.BOTH;
		gridBagConstraints11.gridy = 7;
		gridBagConstraints11.weightx = 1.0;
		gridBagConstraints11.insets = new Insets(28, 0, 0, 0);
		gridBagConstraints11.gridx = 6;
		GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
		gridBagConstraints10.gridx = 5;
		gridBagConstraints10.insets = new Insets(30, 0, 0, 20);
		gridBagConstraints10.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints10.gridy = 7;
		downloadedVersionLabel = new JLabel();
		downloadedVersionLabel.setText(Message
				.getMessage("dbdownloader.downoader.version"));
		GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
		gridBagConstraints9.fill = GridBagConstraints.BOTH;
		gridBagConstraints9.gridy = 6;
		gridBagConstraints9.weightx = 1.0;
		gridBagConstraints9.gridx = 6;
		GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
		gridBagConstraints8.gridx = 5;
		gridBagConstraints8.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints8.insets = new Insets(0, 0, 0, 20);
		gridBagConstraints8.gridy = 6;
		userVersionLabel = new JLabel();
		userVersionLabel.setText(Message
				.getMessage("dbdownloader.user.version"));
		userVersionLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		userVersionLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
		gridBagConstraints7.gridx = 5;
		gridBagConstraints7.insets = new Insets(0, 0, 0, 20);
		gridBagConstraints7.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints7.gridy = 1;
		userDatabaseDirLabel = new JLabel();
		userDatabaseDirLabel.setText(Message
				.getMessage("dbdownloader.user.database.dir.label"));
		userDatabaseDirLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		userDatabaseDirLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
		gridBagConstraints5.fill = GridBagConstraints.BOTH;
		gridBagConstraints5.gridy = 5;
		gridBagConstraints5.weightx = 1.0;
		gridBagConstraints5.gridx = 6;
		GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
		gridBagConstraints4.gridx = 5;
		gridBagConstraints4.insets = new Insets(0, 0, 0, 20);
		gridBagConstraints4.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints4.gridy = 5;
		romsPathLabel = new JLabel();
		romsPathLabel.setText(Message.getMessage("dbdownloader.rom.dir.label"));
		romsPathLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		romsPathLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.gridx = 6;
		GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
		gridBagConstraints3.gridx = 5;
		gridBagConstraints3.anchor = GridBagConstraints.EAST;
		gridBagConstraints3.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints3.insets = new Insets(0, 0, 0, 20);
		gridBagConstraints3.gridy = 4;
		mediaDirLabel = new JLabel();
		mediaDirLabel.setText(Message
				.getMessage("dbdownloader.media.dir.label"));
		mediaDirLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		mediaDirLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.fill = GridBagConstraints.BOTH;
		gridBagConstraints2.gridy = 1;
		gridBagConstraints2.weightx = 0.5;
		gridBagConstraints2.gridx = 6;
		this.setLayout(new GridBagLayout());
		this.setSize(new Dimension(600, 400));
		this.setPreferredSize(getSize());
		this.setMinimumSize(getSize());
		this.setMaximumSize(getSize());
		this.setBackground(Color.white);
		this.add(getUserDatabaseDirField(), gridBagConstraints2);
		this.add(mediaDirLabel, gridBagConstraints3);
		this.add(getMediaDirField(), gridBagConstraints);
		this.add(romsPathLabel, gridBagConstraints4);
		this.add(getRomsPathField(), gridBagConstraints5);
		this.add(userDatabaseDirLabel, gridBagConstraints7);
		this.add(userVersionLabel, gridBagConstraints8);
		this.add(getUserVersionField(), gridBagConstraints9);
		this.add(downloadedVersionLabel, gridBagConstraints10);
		this.add(getDownloadedVersionField(), gridBagConstraints11);
		this.add(hyperlistVersionLabel, gridBagConstraints12);
		this.add(getHyperlistVersionField(), gridBagConstraints13);
		this.add(noteLabel, gridBagConstraints22);
		this.add(getDownloadButton(), gridBagConstraints15);
	}

	/**
	 * This method initializes userDatabaseDirField
	 * 
	 * @return javax.swing.JTextField
	 */
	public JTextField getUserDatabaseDirField() {
		if (userDatabaseDirField == null) {
			userDatabaseDirField = new JTextField();
			userDatabaseDirField.setEnabled(false);
		}
		return userDatabaseDirField;
	}

	/**
	 * This method initializes getMediaDirField
	 * 
	 * @return javax.swing.JTextField
	 */
	public JTextField getMediaDirField() {
		if (mediaDirField == null) {
			mediaDirField = new JTextField();
			mediaDirField.setEnabled(false);
		}
		return mediaDirField;
	}

	/**
	 * This method initializes romsPathField
	 * 
	 * @return javax.swing.JTextField
	 */
	public JTextField getRomsPathField() {
		if (romsPathField == null) {
			romsPathField = new JTextField();
			romsPathField.setEnabled(false);
		}
		return romsPathField;
	}

	/**
	 * This method initializes userVersionField
	 * 
	 * @return javax.swing.JTextField
	 */
	public JTextField getUserVersionField() {
		if (userVersionField == null) {
			userVersionField = new JTextField();
			userVersionField.setEnabled(false);
		}
		return userVersionField;
	}

	/**
	 * This method initializes downloadedVersionField
	 * 
	 * @return javax.swing.JTextField
	 */
	public JTextField getDownloadedVersionField() {
		if (downloadedVersionField == null) {
			downloadedVersionField = new JTextField();
			downloadedVersionField.setEnabled(false);
		}
		return downloadedVersionField;
	}

	/**
	 * This method initializes hyperlistVersionField
	 * 
	 * @return javax.swing.JTextField
	 */
	public JTextField getHyperlistVersionField() {
		if (hyperlistVersionField == null) {
			hyperlistVersionField = new JTextField();
			hyperlistVersionField.setEnabled(false);
			hyperlistVersionField.setEditable(true);
		}
		return hyperlistVersionField;
	}

	/**
	 * This method initializes downloadButton
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getDownloadButton() {
		if (downloadButton == null) {
			downloadButton = new JButton();
			downloadButton.setText(Message
					.getMessage("dbdownloader.upload.database.label"));
			downloadButton.setEnabled(false);
			downloadButton
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							System.out.println("actionPerformed()"); // TODO
																		// Auto-generated
																		// Event
																		// stub
																		// actionPerformed()
							// Process
							new BasicProgressDialog(new DbDowloaderWorker());
							// Update filed
							DbDownLoaderController.instance.updateDetails();
						}
					});
		}
		return downloadButton;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
