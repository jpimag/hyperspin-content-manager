package jps.hyperspin.module.dbmaker.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import jps.hyperspin.common.i18n.Message;
import jps.hyperspin.module.dbmaker.controller.DbMakerController;

public class DbMakerOptionPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JCheckBox noClones = null;
	private JLabel noClonesLabel = null;
	private JCheckBox removeReplacedRoms = null;
	private JLabel removeReplacedRomsLabel = null;
	private JCheckBox useRegionPreference = null;
	private JLabel useRegionPreferenceLael = null;
	private RegionPreferencePanel regionPreferencePanel = null;
	private JCheckBox useDeltaFiles = null;
	private JLabel useDeltaFilesLabel = null;
	private JScrollPane jScrollPane = null;
	private JList deltaFilesList = null;
	private JButton processButton = null;

	/**
	 * This is the default constructor
	 */
	public DbMakerOptionPanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
		gridBagConstraints3.gridx = 0;
		gridBagConstraints3.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints3.gridwidth = 2;
		gridBagConstraints3.insets = new Insets(50, 0, 0, 0);
		gridBagConstraints3.gridy = 6;
		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints2.gridy = 5;
		gridBagConstraints2.weightx = 1.0;
		gridBagConstraints2.weighty = 1.0;
		gridBagConstraints2.anchor = GridBagConstraints.WEST;
		gridBagConstraints2.gridx = 1;
		GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
		gridBagConstraints14.gridx = 1;
		gridBagConstraints14.anchor = GridBagConstraints.WEST;
		gridBagConstraints14.gridy = 4;
		useDeltaFilesLabel = new JLabel();
		useDeltaFilesLabel.setText(Message.getMessage("dbmaker.options.deltafiles.label"));
		GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
		gridBagConstraints13.gridx = 0;
		gridBagConstraints13.gridy = 4;
		GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
		gridBagConstraints12.gridx = 1;
		gridBagConstraints12.gridwidth = 8;
		gridBagConstraints12.ipadx = 0;
		gridBagConstraints12.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints12.gridy = 3;
		GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
		gridBagConstraints11.gridx = 1;
		gridBagConstraints11.anchor = GridBagConstraints.WEST;
		gridBagConstraints11.gridy = 2;
		useRegionPreferenceLael = new JLabel();
		useRegionPreferenceLael.setText(Message.getMessage("dbmaker.options.regionpreference.label"));
		GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
		gridBagConstraints10.gridx = 0;
		gridBagConstraints10.gridy = 2;
		GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
		gridBagConstraints9.gridx = 1;
		gridBagConstraints9.anchor = GridBagConstraints.WEST;
		gridBagConstraints9.gridy = 1;
		removeReplacedRomsLabel = new JLabel();
		removeReplacedRomsLabel.setText(Message.getMessage("dbmaker.options.movereplacedroms.label"));
		GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
		gridBagConstraints8.gridx = 0;
		gridBagConstraints8.gridy = 1;
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.gridx = 1;
		gridBagConstraints1.anchor = GridBagConstraints.WEST;
		gridBagConstraints1.gridy = 0;
		noClonesLabel = new JLabel();
		noClonesLabel.setText(Message.getMessage("dbmaker.options.noclones.label"));
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.anchor = GridBagConstraints.CENTER;
		gridBagConstraints.gridy = 0;
		this.setSize(579, 207);
		this.setLayout(new GridBagLayout());
		this.setBackground(Color.white);
		this.add(getNoClones(), gridBagConstraints);
		this.add(noClonesLabel, gridBagConstraints1);
		this.add(getRemoveReplacedRoms(), gridBagConstraints8);
		this.add(removeReplacedRomsLabel, gridBagConstraints9);
		this.add(getUseRegionPreference(), gridBagConstraints10);
		this.add(useRegionPreferenceLael, gridBagConstraints11);
		this.add(getRegionPreferencePanel(), gridBagConstraints12);
		this.add(getUseDeltaFiles(), gridBagConstraints13);
		this.add(useDeltaFilesLabel, gridBagConstraints14);
		this.add(getJScrollPane(), gridBagConstraints2);
		this.add(getProcessButton(), gridBagConstraints3);
	}

	/**
	 * This method initializes noClones
	 * 
	 * @return javax.swing.JCheckBox
	 */
	public JCheckBox getNoClones() {
		if (noClones == null) {
			noClones = new JCheckBox();
			noClones.setBackground(Color.white);
		}
		return noClones;
	}

	/**
	 * This method initializes removeReplacedRoms
	 * 
	 * @return javax.swing.JCheckBox
	 */
	public JCheckBox getRemoveReplacedRoms() {
		if (removeReplacedRoms == null) {
			removeReplacedRoms = new JCheckBox();
			removeReplacedRoms.setBackground(Color.white);
		}
		return removeReplacedRoms;
	}

	/**
	 * This method initializes useRegionPreference
	 * 
	 * @return javax.swing.JCheckBox
	 */
	public JCheckBox getUseRegionPreference() {
		if (useRegionPreference == null) {
			useRegionPreference = new JCheckBox();
			useRegionPreference.setBackground(Color.white);
			useRegionPreference.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					System.out.println("stateChanged()"); // TODO Auto-generated
															// Event stub
															// stateChanged()
					DbMakerController.instance.toggleUserRegion(useRegionPreference.isSelected());

				}
			});
		}
		return useRegionPreference;
	}

	/**
	 * This method initializes regionPreferencePanel
	 * 
	 * @return jps.hyperspin.module.dbmaker.view.RegionPreferencePanel
	 */
	public RegionPreferencePanel getRegionPreferencePanel() {
		if (regionPreferencePanel == null) {
			regionPreferencePanel = new RegionPreferencePanel();
			regionPreferencePanel.setPreferredSize(new Dimension(500, 76));
			regionPreferencePanel.setBackground(Color.white);
		}
		return regionPreferencePanel;
	}

	/**
	 * This method initializes useDeltaFiles
	 * 
	 * @return javax.swing.JCheckBox
	 */
	public JCheckBox getUseDeltaFiles() {
		if (useDeltaFiles == null) {
			useDeltaFiles = new JCheckBox();
			useDeltaFiles.setBackground(Color.white);
			useDeltaFiles.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					System.out.println("stateChanged()"); // TODO Auto-generated
															// Event stub
															// stateChanged()
					DbMakerController.instance.toggleDeltaFile(useDeltaFiles.isSelected());

				}
			});
		}
		return useDeltaFiles;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setPreferredSize(new Dimension(200, 100));
			jScrollPane.setSize(jScrollPane.getPreferredSize());
			jScrollPane.setMaximumSize(jScrollPane.getPreferredSize());
			jScrollPane.setMinimumSize(jScrollPane.getPreferredSize());
			jScrollPane.setViewportView(getDeltaFilesList());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes deltaFilesList
	 * 
	 * @return javax.swing.JList
	 */
	public JList getDeltaFilesList() {
		if (deltaFilesList == null) {
			deltaFilesList = new JList();
			deltaFilesList.setEnabled(false);
		}
		return deltaFilesList;
	}

	/**
	 * This method initializes processButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getProcessButton() {
		if (processButton == null) {
			processButton = new JButton();
			processButton.setText(Message.getMessage("dbmaker.process.label"));
			processButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("actionPerformed()"); // TODO
																// Auto-generated
																// Event stub
																// actionPerformed()
					DbMakerController.instance.process();
				}
			});
		}
		return processButton;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
