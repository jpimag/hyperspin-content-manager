package jps.hyperspin.module.reportmaker.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import jps.hyperspin.common.i18n.Message;
import jps.hyperspin.module.mediachecker.controller.MediaCheckerController;

public class ReportMakerTab extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton jButton = null; // @jve:decl-index=0:visual-constraint="205,103"

	/**
	 * This is the default constructor
	 */
	public ReportMakerTab() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		this.setSize(800, 400);
		this.setLayout(new GridBagLayout());
		this.setPreferredSize(new Dimension(800, 400));
		this.setBackground(Color.white);
		this.add(getJButton(), gridBagConstraints);

	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText(Message.getMessage("reportmaker.process.label"));
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("actionPerformed()");
					MediaCheckerController.instance.process();
				}
			});
		}
		return jButton;
	}

}
