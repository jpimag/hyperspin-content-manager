package jps.hyperspin.module.dbmaker.worker;

import jps.hyperspin.common.worker.CommonWorker;
import jps.hyperspin.module.dbdownloader.model.DatabaseDetail;
import jps.hyperspin.module.dbdownloader.model.generated.menu.MenuType;
import jps.hyperspin.module.dbmaker.model.DbMakerOption;

/**
 * 
 * @author jps
 * 
 */
public class DeltaGeneratorWorker extends CommonWorker {

	private DeltaGeneratorProcessor processor;

	public DeltaGeneratorWorker(String system, DbMakerOption option, MenuType mainDownloadedDatabase,
			DatabaseDetail detail) {
		super();
		processor = new DeltaGeneratorProcessor(system, option, mainDownloadedDatabase, detail, this,
				MAX_PROGRESS_VALUE);

	}

	@Override
	protected void executeInternal() throws Exception {
		processor.execute();
	}

}
