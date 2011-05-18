package jps.hyperspin.module.dbmaker.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

import jps.hyperspin.common.i18n.Message;

public class NamingConventionPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JRadioButton noIntro = null;
	private JRadioButton redumpOrg = null;
	private JLabel noIntroLabel = null;
	private JLabel redumpOrgLabel = null;
	private JLabel preferredRegionLabel = null;
	private JLabel preferredCountryLabel = null;
	private JComboBox preferredRegion = null;
	private JComboBox preferredCountry = null;
	private JRadioButton other = null;
	private JLabel otherLabel = null;

	/**
	 * This is the default constructor
	 */
	public NamingConventionPanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
		gridBagConstraints10.gridx = 3;
		gridBagConstraints10.anchor = GridBagConstraints.WEST;
		gridBagConstraints10.gridy = 2;
		otherLabel = new JLabel();
		otherLabel.setText(Message.getMessage("dbmaker.nameconvention.other.label"));
		GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
		gridBagConstraints9.gridx = 0;
		gridBagConstraints9.gridy = 2;
		GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
		gridBagConstraints7.fill = GridBagConstraints.BOTH;
		gridBagConstraints7.gridy = 1;
		gridBagConstraints7.weightx = 1.0;
		gridBagConstraints7.gridx = 5;
		GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
		gridBagConstraints6.fill = GridBagConstraints.BOTH;
		gridBagConstraints6.gridy = 0;
		gridBagConstraints6.weightx = 1.0;
		gridBagConstraints6.gridx = 5;
		GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
		gridBagConstraints5.gridx = 4;
		gridBagConstraints5.anchor = GridBagConstraints.WEST;
		gridBagConstraints5.insets = new Insets(0, 50, 0, 0);
		gridBagConstraints5.gridy = 1;
		preferredCountryLabel = new JLabel();
		preferredCountryLabel.setText(Message.getMessage("dbmaker.preferredcountry.label"));
		GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
		gridBagConstraints4.gridx = 4;
		gridBagConstraints4.anchor = GridBagConstraints.WEST;
		gridBagConstraints4.insets = new Insets(0, 50, 0, 0);
		gridBagConstraints4.gridy = 0;
		preferredRegionLabel = new JLabel();
		preferredRegionLabel.setText(Message.getMessage("dbmaker.preferredregion.label"));
		GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
		gridBagConstraints3.gridx = 3;
		gridBagConstraints3.anchor = GridBagConstraints.WEST;
		gridBagConstraints3.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints3.gridy = 1;
		redumpOrgLabel = new JLabel();
		redumpOrgLabel.setText(Message.getMessage("dbmaker.nameconvention.redump.label"));
		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.gridx = 3;
		gridBagConstraints2.anchor = GridBagConstraints.WEST;
		gridBagConstraints2.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints2.gridy = 0;
		noIntroLabel = new JLabel();
		noIntroLabel.setText(Message.getMessage("dbmaker.nameconvention.nointro.label"));
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.gridx = 0;
		gridBagConstraints1.gridy = 1;
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		this.setSize(464, 134);
		this.setLayout(new GridBagLayout());
		this.setBackground(Color.white);
		this.setBorder(BorderFactory.createTitledBorder(null, Message.getMessage("dbmaker.options.naming.label"),
				TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51,
						51)));
		this.add(getNoIntro(), gridBagConstraints);
		this.add(getRedumpOrg(), gridBagConstraints1);
		this.add(noIntroLabel, gridBagConstraints2);
		this.add(redumpOrgLabel, gridBagConstraints3);
		this.add(preferredRegionLabel, gridBagConstraints4);
		this.add(preferredCountryLabel, gridBagConstraints5);
		this.add(getPreferredRegion(), gridBagConstraints6);
		this.add(getPreferredCountry(), gridBagConstraints7);
		this.add(getOther(), gridBagConstraints9);
		this.add(otherLabel, gridBagConstraints10);
	}

	/**
	 * This method initializes noIntro
	 * 
	 * @return javax.swing.JRadioButton
	 */
	public JRadioButton getNoIntro() {
		if (noIntro == null) {
			noIntro = new JRadioButton();
			noIntro.setBackground(Color.white);
			noIntro.setSelected(true);
		}
		return noIntro;
	}

	/**
	 * This method initializes redumpOrg
	 * 
	 * @return javax.swing.JRadioButton
	 */
	public JRadioButton getRedumpOrg() {
		if (redumpOrg == null) {
			redumpOrg = new JRadioButton();
			redumpOrg.setBackground(Color.white);
		}
		return redumpOrg;
	}

	/**
	 * This method initializes preferredRegion
	 * 
	 * @return javax.swing.JComboBox
	 */
	public JComboBox getPreferredRegion() {
		if (preferredRegion == null) {
			preferredRegion = new JComboBox();
			preferredRegion.setBackground(Color.white);
		}
		return preferredRegion;
	}

	/**
	 * This method initializes preferredCountry
	 * 
	 * @return javax.swing.JComboBox
	 */
	public JComboBox getPreferredCountry() {
		if (preferredCountry == null) {
			preferredCountry = new JComboBox();
			preferredCountry.setBackground(Color.white);
		}
		return preferredCountry;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	public JRadioButton getOther() {
		if (other == null) {
			other = new JRadioButton();
			other.setBackground(Color.white);
		}
		return other;
	}

} // @jve:decl-index=0:visual-constraint="10,10"