package jps.hyperspin.main.view;

import java.awt.Dimension;

import javax.swing.JTabbedPane;

import jps.hyperspin.module.dbdownloader.view.DatabaseTab;
import jps.hyperspin.module.dbmaker.presentation.DatabaseActionTab;

public class MainTabbedPane extends JTabbedPane {

	private static final long serialVersionUID = 1L;
	private DatabaseTab databaseTab = null;

	/**
	 * This is the default constructor
	 */
	public MainTabbedPane() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(800, 400);

		this.addTab("Database Downloader", null, getDatabaseTab(), null);
		this.addTab("Database Maker", new DatabaseActionTab());

		this.addTab("Media Checker", new DatabaseActionTab());
		// this.addTab("WHDLoad Tool", new WhdloadTab());
	}

	/**
	 * This method initializes databaseTab
	 * 
	 * @return jps.hyperspin.module.dbdownloader.view.DatabaseTab
	 */
	public DatabaseTab getDatabaseTab() {
		if (databaseTab == null) {
			databaseTab = new DatabaseTab();
			databaseTab.setPreferredSize(new Dimension(800, 400));
		}
		return databaseTab;
	}

}
