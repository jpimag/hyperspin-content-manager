package jps.hyperspin.module.reportmaker.worker;

import jps.hyperspin.common.worker.CommonWorker;
import jps.hyperspin.module.mediachecker.processor.MediaCheckerProcessor;

/**
 * 
 * @author jps
 * 
 */
public class ReportMakerWorker extends CommonWorker {

	private MediaCheckerProcessor processor;

	public ReportMakerWorker() {
		super();
		// processor = new MediaCheckerProcessor(system, option, detail, this,
		// MAX_PROGRESS_VALUE);

	}

	@Override
	protected void executeInternal() throws Exception {
		processor.execute();
	}

}
