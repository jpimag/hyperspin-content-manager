package jps.hyperspin.presentation;

import javax.swing.JTabbedPane;

import jps.hyperspin.module.database.presentation.action.DatabaseActionTab;
import jps.hyperspin.module.database.presentation.main.DatabaseTab;

public class MainTabbedPane extends JTabbedPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private DatabaseTab databaseTab = new DatabaseTab();;

	public void init() {

		this.addTab("Reference Database", databaseTab);
		this.addTab("Database Maker", new DatabaseActionTab(databaseTab));

		this.addTab("Medias", new DatabaseActionTab(databaseTab));
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
