package jps.hyperspin.module.dbmaker.view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import jps.hyperspin.common.i18n.Message;
import jps.hyperspin.module.dbmaker.controller.DbMakerController;

public class DbMakerOptionPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JCheckBox noClones = null;
	private JLabel noClonesLabel = null;
	private JCheckBox removeNotUsedRoms = null;
	private JLabel removeNotUsedRomsLabel = null;
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
		GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
		gridBagConstraints12.gridx = 0;
		gridBagConstraints12.gridwidth = 8;
		gridBagConstraints12.ipadx = 0;
		gridBagConstraints12.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints12.gridy = 3;
		GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
		gridBagConstraints11.gridx = 1;
		gridBagConstraints11.anchor = GridBagConstraints.WEST;
		gridBagConstraints11.gridy = 2;
		GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
		gridBagConstraints10.gridx = 0;
		gridBagConstraints10.gridy = 2;
		GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
		gridBagConstraints9.gridx = 1;
		gridBagConstraints9.anchor = GridBagConstraints.WEST;
		gridBagConstraints9.gridy = 1;
		GridBagConstraints gridBagConstraints19 = new GridBagConstraints();
		gridBagConstraints19.gridx = 1;
		gridBagConstraints19.anchor = GridBagConstraints.WEST;
		gridBagConstraints19.gridy = 2;
		removeNotUsedRomsLabel = new JLabel();
		removeNotUsedRomsLabel.setText(Message.getMessage("dbmaker.options.movenotusedroms.label"));
		GridBagConstraints gridBagConstraints18 = new GridBagConstraints();
		gridBagConstraints18.gridx = 0;
		gridBagConstraints18.gridy = 2;
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
		this.setSize(579, 250);
		this.setLayout(new GridBagLayout());
		this.setBackground(Color.white);
		this.add(getNoClones(), gridBagConstraints);
		this.add(noClonesLabel, gridBagConstraints1);
		this.add(getRemoveNotUsedRoms(), gridBagConstraints18);
		this.add(removeNotUsedRomsLabel, gridBagConstraints19);
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
	 * /** This method initializes removeNotUsedRoms
	 * 
	 * @return javax.swing.JCheckBox
	 */
	public JCheckBox getRemoveNotUsedRoms() {
		if (removeNotUsedRoms == null) {
			removeNotUsedRoms = new JCheckBox();
			removeNotUsedRoms.setBackground(Color.white);
		}
		return removeNotUsedRoms;
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
					DbMakerController.instance.process();
				}
			});
		}
		return processButton;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
