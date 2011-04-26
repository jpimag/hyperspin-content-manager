package jps.hyperspin.main.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;

import jps.hyperspin.common.i18n.Message;
import jps.hyperspin.common.view.BasicProgressDialog;
import jps.hyperspin.main.model.Systems;
import jps.hyperspin.module.dbdownloader.worker.CheckDatabaseVersionWorker;

public class SystemTablePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton checkButton = null;
	private JScrollPane jScrollPane = null;
	private JTable jTable = null;

	/**
	 * This method initializes
	 * 
	 */
	public SystemTablePanel() {
		super();
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
		this.setSize(new Dimension(400, 800));

		this.setPreferredSize(getSize());
		this.setMinimumSize(getSize());
		this.setMaximumSize(getSize());
		this.add(getCheckButton(), gridBagConstraints);
		this.add(getJScrollPane(), gridBagConstraints1);
	}

	/**
	 * This method initializes checkButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getCheckButton() {
		if (checkButton == null) {
			checkButton = new JButton();
			checkButton.setText(Message
					.getMessage("dbdownloader.system.check.label"));
			checkButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("actionPerformed()"); // TODO
																// Auto-generated
																// Event stub
																// actionPerformed()
					for (String system : Systems.instance.list()) {
						// worker
						CheckDatabaseVersionWorker worker = new CheckDatabaseVersionWorker(
								system);

						new BasicProgressDialog(worker);

					}
				}
			});
		}
		return checkButton;
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
			jTable = new JTable(new SystemTableModel());
			jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			jTable.setSize(new Dimension(400, 800));
			jTable.setDefaultRenderer(String.class,
					new SystemTableCellRenderer());

		}
		return jTable;
	}

	public void repaint() {

	}

	public String getSelection() {
		return Systems.instance.get(jTable.getSelectedRow());
	}

	public void addListSelectionListener(ListSelectionListener listener) {
		jTable.getSelectionModel().addListSelectionListener(listener);
	}

} // @jve:decl-index=0:visual-constraint="10,10"
