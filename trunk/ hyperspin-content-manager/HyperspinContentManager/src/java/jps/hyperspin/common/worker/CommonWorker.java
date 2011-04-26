package jps.hyperspin.common.worker;

import javax.swing.SwingWorker;

import jps.hyperspin.main.controller.CommonLogger;

public abstract class CommonWorker extends SwingWorker<Void, Void> {

	public static final int MAX_PROGRESS_VALUE = 100;

	/**
	 * Constructor
	 * 
	 * @param progressBarDialog
	 */
	public CommonWorker() {
		super();
	}

	/**
	 * {@inheritDoc} Executed in event dispatching thread
	 */
	@Override
	public void done() {
		super.done();
		// MainClass.mainFrame.pack();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected final Void doInBackground() throws Exception {
		try {
			executeInternal();
		} catch (Exception e) {
			// TODO add error dialog
			CommonLogger.instance.info("Erreur : " + e);
		}
		return null;
	}

	protected abstract void executeInternal() throws Exception;

	/**
	 * Get remaining process value
	 * 
	 * @return
	 */
	protected int getRemainingProcess() {
		return 100 - getProgress();
	}

	/**
	 * add progress value according to a percent of the 'until' process value
	 * target.
	 * 
	 * @param currentStep
	 *            current step
	 * @param nbstep
	 *            number total of step
	 * @param progress
	 *            reached when all steps will be achieved
	 * 
	 * @return
	 */
	protected int computeProgressFromAchievedStep(int currentStep, int nbstep,
			int until) {
		double percent = (double) currentStep / (double) nbstep;
		double progress = getProgress() + (percent * (until - getProgress()));
		System.out.println(progress);
		return Math.min(99, (int) progress);
	}

}
