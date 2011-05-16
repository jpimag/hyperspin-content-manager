package jps.hyperspin.common.processor;

import jps.hyperspin.common.worker.CommonWorker;

public abstract class CommonProcessor {

	private CommonWorker worker;
	private int untilProgress;

	/**
	 * Constructor
	 * 
	 * @param progressBarDialog
	 */
	public CommonProcessor(CommonWorker worker, int untilProgress) {
		super();
		this.worker = worker;
		this.untilProgress = untilProgress;
	}

	public abstract void execute() throws Exception;

	/**
	 * 
	 * @param i
	 */
	public void setProgress(int i) {
		worker.setProgressFromAchievedStep(i, CommonWorker.MAX_PROGRESS_VALUE, untilProgress);
	}

	/**
	 * 
	 * @param i
	 */
	public void addProgress(int i) {
		worker.setProgressFromAchievedStep(i, CommonWorker.MAX_PROGRESS_VALUE, untilProgress);
	}

}
