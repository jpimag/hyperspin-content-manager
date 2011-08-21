package jps.hyperspin.module.reportmaker.processor;

import jps.hyperspin.common.processor.CommonProcessor;
import jps.hyperspin.common.worker.CommonWorker;
import jps.hyperspin.main.controller.CommonLogger;
import jps.hyperspin.main.model.Systems;
import jps.hyperspin.module.dbmaker.model.DbMakerOption;
import jps.hyperspin.module.dbmaker.processor.DbMakerProcessor.DbMakerResult;
import jps.hyperspin.module.mediachecker.model.MediaCheckerOption;
import jps.hyperspin.module.mediachecker.processor.MediaCheckerProcessor.MediaCheckerResult;

public class ReportMakerProcessor extends CommonProcessor {

	protected class SystemResult {
		public DbMakerResult dbResult;
		public MediaCheckerResult wheelResult;
		public MediaCheckerResult artwork1Result;
		public MediaCheckerResult artwork2Result;
		public MediaCheckerResult artwork3Result;
		public MediaCheckerResult artwork4Result;
		public MediaCheckerResult themeResult;
		public MediaCheckerResult videoResult;

	}

	public ReportMakerProcessor(CommonWorker worker, int untilProgress) {
		super(worker, untilProgress);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute() throws Exception {

		for (String system : Systems.instance.list()) {
			// For each system
			DbMakerOption dbOption = DbMakerOption.load();
			// TODO

			MediaCheckerOption mediaOption = MediaCheckerOption.load();
			// TODO
		}
		CommonLogger.instance.info("Report generated");
	}

}
