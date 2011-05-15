package jps.hyperspin.module.dbmaker.worker;

import jps.hyperspin.common.worker.CommonWorker;
import jps.hyperspin.module.dbdownloader.model.DatabaseDetail;
import jps.hyperspin.module.dbmaker.model.DbMakerOption;

/**
 * 
 * @author jps
 * 
 */
public class DbMakerWorker extends CommonWorker {

	private DbMakerProcessor processor;

	public DbMakerWorker(String system, DbMakerOption option, DatabaseDetail detail) {
		super();
		processor = new DbMakerProcessor(system, option, detail, this, MAX_PROGRESS_VALUE);

	}

	@Override
	protected void executeInternal() throws Exception {
		processor.execute();
	}

}
