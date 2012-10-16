package jps.hyperspin.module.reportmaker.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import jps.hyperspin.common.i18n.Message;
import jps.hyperspin.module.reportmaker.controller.ReportMakerController;
import jps.hyperspin.module.reportmaker.model.Report;

public class ReportMakerTab extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton launchButton = null; // @jve:decl-index=0:visual-constraint="205,103"
	private JButton viewButton = null; // @jve:decl-index=0:visual-constraint="205,103"
	private JLabel launchSubLabel = null;

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
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.gridx = 0;
		gridBagConstraints1.gridy = 1;
		launchSubLabel = new JLabel();
		launchSubLabel.setText(Message.getMessage("reportmaker.process.sublabel"));
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		this.setSize(800, 400);
		this.setLayout(new GridBagLayout());
		this.setPreferredSize(new Dimension(800, 400));
		this.setBackground(Color.white);
		this.add(getLaunchButton(), gridBagConstraints);

		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.insets = new Insets(50, 0, 0, 0);
		gridBagConstraints2.gridx = 0;
		gridBagConstraints2.gridy = 2;
		this.add(getViewButton(), gridBagConstraints2);
		this.add(launchSubLabel, gridBagConstraints1);
	}

	/**
	 * This method initializes launchButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getLaunchButton() {
		if (launchButton == null) {
			launchButton = new JButton();
			launchButton.setText(Message.getMessage("reportmaker.process.label"));
			launchButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ReportMakerController.instance.process();
				}
			});
		}
		return launchButton;
	}

	/**
	 * This method initializes viewButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getViewButton() {
		if (viewButton == null) {
			viewButton = new JButton();
			viewButton.setText(Message.getMessage("reportmaker.view.label"));
			viewButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					displayReport();
				}
			});
		}
		return viewButton;
	}

	public void displayReport() {
		// Load report
		Report report = Report.load();

		// Display report
		ReportFrame reportFrame = new ReportFrame(report);
		reportFrame.setResizable(false);
		reportFrame.setVisible(true);

	}
}
