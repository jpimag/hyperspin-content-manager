package jps.hyperspin.module.dbdownloader.worker;

import jps.hyperspin.common.worker.CommonWorker;
import jps.hyperspin.main.model.Systems;
import jps.hyperspin.module.dbdownloader.processor.CheckDatabaseVersionProcessor;

/**
 * This worker in in charge to check database version for all systems.
 * 
 * @author jps
 * 
 */
public class CheckAllDatabaseVersionWorker extends CommonWorker {

	public CheckAllDatabaseVersionWorker() {
		super();

	}

	@Override
	protected void executeInternal() throws Exception {
		for (String system : Systems.instance.list()) {
			CheckDatabaseVersionProcessor processor = new CheckDatabaseVersionProcessor(system, this,
					MAX_PROGRESS_VALUE);
			processor.execute();
		}
	}

}
