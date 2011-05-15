package jps.hyperspin.module.dbdownloader.worker;

import jps.hyperspin.common.worker.CommonWorker;

/**
 * This worker in in charge to check database version for the selected system.
 * 
 * @author jps
 * 
 */
public class DbDowloaderWorker extends CommonWorker {

	private DbDownloaderProcessor processor;

	public DbDowloaderWorker() {
		super();
		processor = new DbDownloaderProcessor(this, MAX_PROGRESS_VALUE);

	}

	@Override
	protected void executeInternal() throws Exception {
		processor.execute();
	}
}
