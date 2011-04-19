package jps.hyperspin.presentation;

import javax.swing.JTabbedPane;

import jps.hyperspin.module.database.presentation.downloader.DatabaseTab;
import jps.hyperspin.module.dbmaker.presentation.DatabaseActionTab;

public class MainTabbedPane extends JTabbedPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private DatabaseTab databaseTab = new DatabaseTab();;

	public void init() {

		this.addTab("Database Downloader", databaseTab);
		this.addTab("Database Maker", new DatabaseActionTab(databaseTab));

		this.addTab("Media Checker", new DatabaseActionTab(databaseTab));
		// this.addTab("WHDLoad Tool", new WhdloadTab());
	}

	/**
	 * @return the databaseTab
	 */
	public DatabaseTab getDatabaseTab() {
		return databaseTab;
	}

	/**
	 * @param databaseTab
	 *            the databaseTab to set
	 */
	public void setDatabaseTab(DatabaseTab databaseTab) {
		this.databaseTab = databaseTab;
	}

}
