package jps.hyperspin.module.dbdownloader.worker;

import jps.hyperspin.common.worker.CommonWorker;

/**
 * This worker in in charge to check database version for the selected system.
 * 
 * @author jps
 * 
 */
public class CheckDatabaseVersionWorker extends CommonWorker {

	private CheckDatabaseVersionProcessor processor;

	public CheckDatabaseVersionWorker(String system) {
		super();
		processor = new CheckDatabaseVersionProcessor(system, this, MAX_PROGRESS_VALUE);

	}

	@Override
	protected void executeInternal() throws Exception {
		processor.execute();
	}

	/**
	 * @return the processor
	 */
	public CheckDatabaseVersionProcessor getProcessor() {
		return processor;
	}
}
