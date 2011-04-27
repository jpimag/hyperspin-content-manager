package jps.hyperspin.module.dbmaker.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import jps.hyperspin.common.i18n.Message;

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
		removeReplacedRomsLabel.setText(Message.getMessage("dbmaker.options.removereplaced.label"));
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
	}

	/**
	 * This method initializes noClones
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getNoClones() {
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
	private JCheckBox getRemoveReplacedRoms() {
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
	private JCheckBox getUseRegionPreference() {
		if (useRegionPreference == null) {
			useRegionPreference = new JCheckBox();
			useRegionPreference.setBackground(Color.white);
		}
		return useRegionPreference;
	}

	/**
	 * This method initializes regionPreferencePanel
	 * 
	 * @return jps.hyperspin.module.dbmaker.view.RegionPreferencePanel
	 */
	private RegionPreferencePanel getRegionPreferencePanel() {
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
	private JCheckBox getUseDeltaFiles() {
		if (useDeltaFiles == null) {
			useDeltaFiles = new JCheckBox();
			useDeltaFiles.setBackground(Color.white);
		}
		return useDeltaFiles;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
