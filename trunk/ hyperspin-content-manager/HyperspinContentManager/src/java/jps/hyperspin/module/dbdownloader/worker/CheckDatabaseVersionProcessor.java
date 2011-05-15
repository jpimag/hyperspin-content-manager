package jps.hyperspin.module.dbdownloader.worker;

import java.io.FileNotFoundException;

import jps.hyperspin.common.DatabaseUtilities;
import jps.hyperspin.common.worker.CommonWorker;
import jps.hyperspin.main.controller.MainController;
import jps.hyperspin.main.model.VersionStatut;
import jps.hyperspin.module.dbdownloader.model.generated.menu.MenuType;

public class CheckDatabaseVersionProcessor extends AbstractDbDownloaderProcessor {

	public MenuType downloadedDatabase;
	public MenuType hyperlistDatabase;
	public MenuType userDatabase;
	private String system;

	public CheckDatabaseVersionProcessor(String system, CommonWorker worker, int untilProgress) {
		super(worker, untilProgress);
		this.system = system;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute() throws Exception {

		if (system != null) {

			// User Database version
			try {
				userDatabase = DatabaseUtilities.loadDatabase(DatabaseUtilities.getUserDatabasePath(system));

			} catch (FileNotFoundException e) {
				userDatabase = null;
			}
			setProgress(25);

			// Downloaded Database version
			try {
				downloadedDatabase = DatabaseUtilities
						.loadDatabase(DatabaseUtilities.getDownloadedDatabasePath(system));

			} catch (FileNotFoundException e) {
				downloadedDatabase = null;
			}
			setProgress(50);

			// Check DB from hyperlist web site
			hyperlistDatabase = getLastAvailableDb(system);

			// Notify system list panel
			MainController.instance.changeSystemStatut(system, getVersionStatut());
		}

	}

	public VersionStatut getVersionStatut() {
		if (hyperlistDatabase == null) {
			return VersionStatut.SYSTEM_NOT_AVAILABLE;
		} else if (hyperlistDatabase.getHeader() == null) {
			return VersionStatut.SYSTEM_NOT_VERSIONNED;
		} else if (downloadedDatabase == null
				|| downloadedDatabase.getHeader() == null
				|| !downloadedDatabase.getHeader().getListversion()
						.equals(hyperlistDatabase.getHeader().getListversion())) {
			return VersionStatut.OUT_DATED_DOWNLOADED_DB;

		} else if (userDatabase == null || userDatabase.getHeader() == null
				|| !userDatabase.getHeader().getListversion().equals(downloadedDatabase.getHeader().getListversion())) {
			return VersionStatut.OUT_DATED_USER_DB;
		} else {
			return VersionStatut.UP_TO_DATE;

		}

	}
}
