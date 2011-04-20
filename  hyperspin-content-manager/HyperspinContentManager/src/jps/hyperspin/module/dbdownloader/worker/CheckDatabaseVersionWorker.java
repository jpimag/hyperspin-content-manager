package jps.hyperspin.module.dbdownloader.worker;

import java.io.FileNotFoundException;

import jps.hyperspin.MainClass;
import jps.hyperspin.common.DatabaseUtilities;
import jps.hyperspin.exception.HCMDatabaseException;
import jps.hyperspin.module.dbdownloader.model.MenuType;

/**
 * This worker in in charge to check database version for the selected system.
 * 
 * @author jps
 * 
 */
public class CheckDatabaseVersionWorker extends AbstractDbDownloaderWorker {

	private MenuType currentDatabase;
	private MenuType lastDatabase;

	public CheckDatabaseVersionWorker() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Void doInBackground() throws Exception {
		try {
			setProgress(1);

			String system = MainClass.mainFrame.getSystemSelected();
			if (system != null) {

				// Database version
				try {
					currentDatabase = DatabaseUtilities.loadDatabase(
							DatabaseUtilities.getReferenceDatabasePath(),
							MainClass.mainFrame.getLogger());

				} catch (FileNotFoundException e) {
					currentDatabase = null;
				}
				setProgress(40);

				// Check DB from hyperlist web site
				lastDatabase = getLastAvailableDb();

				setProgress(100);

			}
		} catch (HCMDatabaseException e) {
			MainClass.mainFrame.getLogger().info("Erreur : " + e);
		}
		return null;

	}

	/**
	 * @return the currentDatabase
	 */
	protected MenuType getCurrentDatabase() {
		return currentDatabase;
	}

	/**
	 * @return the lastDatabase
	 */
	protected MenuType getLastDatabase() {
		return lastDatabase;
	}

}
