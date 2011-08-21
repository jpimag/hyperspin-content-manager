package jps.hyperspin.module.mediachecker.worker;

import jps.hyperspin.common.worker.CommonWorker;
import jps.hyperspin.module.dbdownloader.model.DatabaseDetail;
import jps.hyperspin.module.mediachecker.model.MediaCheckerOption;
import jps.hyperspin.module.mediachecker.processor.MediaCheckerProcessor;

/**
 * 
 * @author jps
 * 
 */
public class MediaCheckerWorker extends CommonWorker {

	private MediaCheckerProcessor processor;

	public MediaCheckerWorker(String system, MediaCheckerOption option, DatabaseDetail detail) {
		super();
		processor = new MediaCheckerProcessor(system, option, detail, this, MAX_PROGRESS_VALUE);

	}

	@Override
	protected void executeInternal() throws Exception {
		processor.execute();
	}

}
