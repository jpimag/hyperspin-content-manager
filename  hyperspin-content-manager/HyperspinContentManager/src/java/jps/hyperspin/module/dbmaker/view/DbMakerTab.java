package jps.hyperspin.module.dbmaker.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

public class DbMakerTab extends JPanel {

	private static final long serialVersionUID = 1L;
	private DbMakerOptionPanel dbMakerOptionPanel = null;

	/**
	 * This is the default constructor
	 */
	public DbMakerTab() {
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
		this.add(getDbMakerOptionPanel(), gridBagConstraints);
	}

	/**
	 * This method initializes dbMakerOptionPanel
	 * 
	 * @return jps.hyperspin.module.dbmaker.view.DbMakerOptionPanel
	 */
	public DbMakerOptionPanel getDbMakerOptionPanel() {
		if (dbMakerOptionPanel == null) {
			dbMakerOptionPanel = new DbMakerOptionPanel();
			dbMakerOptionPanel.setBackground(Color.white);
		}
		return dbMakerOptionPanel;
	}

}
