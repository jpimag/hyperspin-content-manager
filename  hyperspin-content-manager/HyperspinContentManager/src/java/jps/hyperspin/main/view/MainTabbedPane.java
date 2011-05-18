package jps.hyperspin.main.view;

import java.awt.Dimension;

import javax.swing.JTabbedPane;

import jps.hyperspin.module.dbdownloader.view.DatabaseTab;
import jps.hyperspin.module.dbmaker.view.DbMakerTab;
import jps.hyperspin.module.mediachecker.view.MediaCheckerTab;

public class MainTabbedPane extends JTabbedPane {

	private static final long serialVersionUID = 1L;
	private DatabaseTab dbDownloaderTab = null;
	private DbMakerTab dbMakerTab = null;
	private MediaCheckerTab mediaCheckerTab = null;

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

		this.addTab("Database Downloader", null, getDbDownloaderTab(), null);

		this.addTab("Database Maker", null, getDbMakerTab(), null);

		this.addTab("Media Checker", getMediaCheckerTab());
		// this.addTab("WHDLoad Tool", new WhdloadTab());
	}

	/**
	 * This method initializes dbDownloaderTab
	 * 
	 * @return jps.hyperspin.module.dbdownloader.view.DatabaseTab
	 */
	public DatabaseTab getDbDownloaderTab() {
		if (dbDownloaderTab == null) {
			dbDownloaderTab = new DatabaseTab();
			dbDownloaderTab.setPreferredSize(new Dimension(800, 400));
		}
		return dbDownloaderTab;
	}

	/**
	 * This method initializes dbMakerTab
	 * 
	 * @return jps.hyperspin.module.dbMaker.view.dbMakerTab
	 */
	public DbMakerTab getDbMakerTab() {
		if (dbMakerTab == null) {
			dbMakerTab = new DbMakerTab();
			dbMakerTab.setPreferredSize(new Dimension(800, 400));
		}
		return dbMakerTab;
	}

	/**
	 * This method initializes dbMakerTab
	 * 
	 * @return jps.hyperspin.module.dbMaker.view.dbMakerTab
	 */
	public MediaCheckerTab getMediaCheckerTab() {
		if (mediaCheckerTab == null) {
			mediaCheckerTab = new MediaCheckerTab();
			mediaCheckerTab.setPreferredSize(new Dimension(800, 400));
		}
		return mediaCheckerTab;
	}
}