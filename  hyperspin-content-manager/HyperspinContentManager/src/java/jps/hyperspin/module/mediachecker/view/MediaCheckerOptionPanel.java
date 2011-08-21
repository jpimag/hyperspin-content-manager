package jps.hyperspin.module.mediachecker.view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import jps.hyperspin.common.i18n.Message;
import jps.hyperspin.module.mediachecker.controller.MediaCheckerController;

public class MediaCheckerOptionPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JComboBox jComboBox = null;
	private JCheckBox manualCheckBox = null;
	private JLabel manualLabel = null;
	private JCheckBox purgeCheckBox = null;
	private JLabel purgeLabel = null;
	private JButton processButton = null;

	/**
	 * This is the default constructor
	 */
	public MediaCheckerOptionPanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
		gridBagConstraints8.gridx = 0;
		gridBagConstraints8.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints8.gridwidth = 3;
		gridBagConstraints8.gridy = 3;
		gridBagConstraints8.insets = new Insets(50, 0, 0, 0);
		GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
		gridBagConstraints7.gridx = 1;
		gridBagConstraints7.anchor = GridBagConstraints.WEST;
		gridBagConstraints7.gridy = 2;
		purgeLabel = new JLabel();
		purgeLabel.setText(Message.getMessage("dbmaker.purge.label"));
		GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
		gridBagConstraints6.gridx = 0;
		gridBagConstraints6.gridy = 2;
		GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
		gridBagConstraints3.gridx = 1;
		gridBagConstraints3.anchor = GridBagConstraints.WEST;
		gridBagConstraints3.gridy = 1;
		manualLabel = new JLabel();
		manualLabel.setText(Message.getMessage("dbmaker.manual.label"));
		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.gridx = 0;
		gridBagConstraints2.gridy = 1;
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.weightx = 3.0D;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.gridx = 0;
		this.setSize(579, 207);
		this.setLayout(new GridBagLayout());
		this.setBackground(Color.white);
		this.add(getJComboBox(), gridBagConstraints);
		this.add(getManualCheckBox(), gridBagConstraints2);
		this.add(manualLabel, gridBagConstraints3);
		this.add(getPurgeCheckBox(), gridBagConstraints6);
		this.add(purgeLabel, gridBagConstraints7);
		this.add(getProcessButton(), gridBagConstraints8);
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	public JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
		}
		return jComboBox;
	}

	/**
	 * This method initializes manualCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	public JCheckBox getManualCheckBox() {
		if (manualCheckBox == null) {
			manualCheckBox = new JCheckBox();
			manualCheckBox.setBackground(Color.white);
		}
		return manualCheckBox;
	}

	/**
	 * This method initializes purgeCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	public JCheckBox getPurgeCheckBox() {
		if (purgeCheckBox == null) {
			purgeCheckBox = new JCheckBox();
			purgeCheckBox.setBackground(Color.white);
		}
		return purgeCheckBox;
	}

	/**
	 * This method initializes processButton
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getProcessButton() {
		if (processButton == null) {
			processButton = new JButton();
			processButton.setText(Message.getMessage("mediachecker.process.label"));
			processButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("actionPerformed()");
					MediaCheckerController.instance.process();
				}
			});
		}
		return processButton;
	}

}
