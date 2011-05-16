package jps.hyperspin.module.dbmaker.worker;

import jps.hyperspin.common.worker.CommonWorker;
import jps.hyperspin.module.dbdownloader.model.DatabaseDetail;
import jps.hyperspin.module.dbmaker.model.DbMakerOption;
import jps.hyperspin.module.dbmaker.processor.DbMakerProcessor;
import jps.hyperspin.module.dbmaker.processor.DeltaGeneratorProcessor;

/**
 * 
 * @author jps
 * 
 */
public class DbMakerWorker extends CommonWorker {

	private DeltaGeneratorProcessor deltaProcessor;
	private DbMakerProcessor processor;

	public DbMakerWorker(String system, DbMakerOption option, DatabaseDetail detail) {
		super();
		deltaProcessor = new DeltaGeneratorProcessor(system, option, detail, this, MAX_PROGRESS_VALUE / 2);
		processor = new DbMakerProcessor(system, option, detail, this, MAX_PROGRESS_VALUE);

	}

	@Override
	protected void executeInternal() throws Exception {
		deltaProcessor.execute();
		processor.execute();
	}

}
