package jps.hyperspin.module.dbdownloader.worker;

import java.io.FileNotFoundException;

import jps.hyperspin.MainClass;
import jps.hyperspin.common.DatabaseUtilities;
import jps.hyperspin.module.dbdownloader.model.MenuType;

/**
 * This worker in in charge to check database version for the selected system.
 * 
 * @author jps
 * 
 */
public class CheckDatabaseVersionWorker extends AbstractDbDownloaderWorker {

	protected MenuType downloadedDatabase;
	protected MenuType hyperlistDatabase;
	protected MenuType userDatabase;

	/**
	 * The statut of the user database configuration
	 * 
	 */
	protected enum VersionStatut {
		SYSTEM_NOT_AVAILABLE, SYSTEM_NOT_VERSIONNED, OLD_DOWNLOADED_DB, OLD_USER_DB, UP_TO_DATE;
	}

	public CheckDatabaseVersionWorker() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void executeInternal() throws Exception {
		try {
			setProgress(1);

			String system = MainClass.mainFrame.getSystemSelected();
			if (system != null) {

				// User Database version
				try {
					userDatabase = DatabaseUtilities.loadDatabase(
							DatabaseUtilities.getUserDatabasePath(),
							MainClass.mainFrame.getLogger());

				} catch (FileNotFoundException e) {
					userDatabase = null;
				}
				setProgress(25);

				// Downloaded Database version
				try {
					downloadedDatabase = DatabaseUtilities.loadDatabase(
							DatabaseUtilities.getDownloadedDatabasePath(),
							MainClass.mainFrame.getLogger());

				} catch (FileNotFoundException e) {
					downloadedDatabase = null;
				}
				setProgress(50);

				// Check DB from hyperlist web site
				hyperlistDatabase = getLastAvailableDb();

			}
		} finally {
			setProgress(100);

		}

	}

	protected VersionStatut getVersionStatut() {
		if (hyperlistDatabase == null) {
			return VersionStatut.SYSTEM_NOT_AVAILABLE;
		} else if (hyperlistDatabase.getHeader() == null) {
			return VersionStatut.SYSTEM_NOT_VERSIONNED;
		} else if (downloadedDatabase == null
				|| downloadedDatabase.getHeader() == null
				|| !downloadedDatabase.getHeader().getListversion()
						.equals(hyperlistDatabase.getHeader().getListversion())) {
			return VersionStatut.OLD_DOWNLOADED_DB;

		} else if (userDatabase == null
				|| userDatabase.getHeader() == null
				|| !userDatabase
						.getHeader()
						.getListversion()
						.equals(downloadedDatabase.getHeader().getListversion())) {
			return VersionStatut.OLD_USER_DB;
		} else {
			return VersionStatut.UP_TO_DATE;

		}

	}
}
