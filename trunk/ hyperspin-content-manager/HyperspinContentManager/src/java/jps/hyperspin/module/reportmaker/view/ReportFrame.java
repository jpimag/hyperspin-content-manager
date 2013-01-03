package jps.hyperspin.module.reportmaker.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.SystemColor;

import javax.swing.BorderFactory;
import javax.swing.JFrame;

import jps.hyperspin.common.i18n.Message;
import jps.hyperspin.module.reportmaker.model.Report;

public class ReportFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ReportTablePanel reportTablePanel = null;
	private Report report;

	/**
	 * This method initializes
	 * 
	 */
	public ReportFrame(Report report) {
		super();
		this.report = report;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		setTitle(Message.getMessage("reportmaker.report.title") + " " + report.date);
		this.setSize(new Dimension(1200, 768));
		this.setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridheight = 3;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints.weightx = 0.5;
		gridBagConstraints.weighty = 0.0;
		gridBagConstraints.gridy = 0;
		add(getReportTabletPanel(), gridBagConstraints);

		this.setSize((int) getToolkit().getScreenSize().getWidth(),
				((int) getToolkit().getScreenSize().getHeight() - 40));
	}

	/**
	 * This method initializes systemListPanel
	 * 
	 * @return jps.hyperspin.main.view.SystemListPanel
	 */
	public ReportTablePanel getReportTabletPanel() {
		if (reportTablePanel == null) {
			reportTablePanel = new ReportTablePanel(report);
			reportTablePanel.setBorder(BorderFactory.createLineBorder(SystemColor.activeCaption, 5));
		}
		return reportTablePanel;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
