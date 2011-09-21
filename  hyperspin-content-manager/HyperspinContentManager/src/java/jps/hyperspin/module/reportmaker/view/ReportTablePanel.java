package jps.hyperspin.module.reportmaker.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import jps.hyperspin.module.reportmaker.model.Report;

public class ReportTablePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JScrollPane jScrollPane = null;
	private JTable jTable = null; // @jve:decl-index=0:
	private Report report;

	/**
	 * This method initializes
	 * 
	 */
	public ReportTablePanel(Report report) {
		super();
		this.report = report;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.fill = GridBagConstraints.BOTH;
		gridBagConstraints1.gridy = 0;
		gridBagConstraints1.weightx = 1.0;
		gridBagConstraints1.weighty = 1.0;
		gridBagConstraints1.gridx = 0;
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		this.setLayout(new GridBagLayout());
		this.setSize(new Dimension(800, 800));

		this.setPreferredSize(getSize());
		this.setMinimumSize(getSize());
		this.setMaximumSize(getSize());
		this.add(getJScrollPane(), gridBagConstraints1);
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable(new ReportTableModel(report));
			jTable.setSize(new Dimension(400, 800));
			jTable.setDefaultRenderer(String.class, new ReportTableCellRenderer());

		}
		return jTable;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
