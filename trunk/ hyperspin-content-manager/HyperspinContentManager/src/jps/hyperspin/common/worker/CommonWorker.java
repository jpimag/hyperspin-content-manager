package jps.hyperspin.common.worker;

import javax.swing.SwingWorker;

public abstract class CommonWorker extends SwingWorker<Void, Void> {

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
	}

}
