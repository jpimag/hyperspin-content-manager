package jps.hyperspin.module.dbdownloader.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

public class DatabaseTab extends JPanel {

	private static final long serialVersionUID = 1L;
	private DatabaseDetailPanel databaseDetailPanel = null;

	/**
	 * This is the default constructor
	 */
	public DatabaseTab() {
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
		this.setBackground(Color.white);
		this.add(getDatabaseDetailPanel(), gridBagConstraints);
	}

	/**
	 * This method initializes databaseDetailPanel
	 * 
	 * @return jps.hyperspin.module.dbdownloader.view.DatabaseDetailPanel
	 */
	public DatabaseDetailPanel getDatabaseDetailPanel() {
		if (databaseDetailPanel == null) {
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = GridBagConstraints.BOTH;
			gridBagConstraints1.gridy = 14;
			gridBagConstraints1.weightx = 1.0;
			gridBagConstraints1.weighty = 1.0;
			gridBagConstraints1.gridx = 5;
			databaseDetailPanel = new DatabaseDetailPanel();
			databaseDetailPanel.setPreferredSize(new Dimension(550, 400));
			databaseDetailPanel.setEnabled(false);
		}
		return databaseDetailPanel;
	}

}
