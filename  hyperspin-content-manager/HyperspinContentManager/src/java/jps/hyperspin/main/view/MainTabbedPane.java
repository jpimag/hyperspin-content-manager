package jps.hyperspin.main.view;

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
		this.setSize(300, 200);

		this.addTab("Database Downloader", null, getDatabaseTab(), null);
		this.addTab("Database Maker", new DatabaseActionTab(databaseTab));

		this.addTab("Media Checker", new DatabaseActionTab(databaseTab));
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
		}
		return databaseTab;
	}

}
