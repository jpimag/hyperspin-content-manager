package jps.hyperspin.main.controller;

import jps.hyperspin.common.log.Logger;
import jps.hyperspin.common.log.LoggerLevel;
import jps.hyperspin.common.view.PanelLogger;

public class CommonLogger implements Logger {

	public static final CommonLogger instance = new CommonLogger();

	private PanelLogger panel;

	/**
	 * 
	 * @param panel
	 */
	public void setPanel(PanelLogger panel) {
		this.panel = panel;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void info(final String message) {
		panel.append(message, LoggerLevel.INFO);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void trace(final String message) {
		panel.append(message, LoggerLevel.TRACE);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void error(final String message) {
		panel.append(message, LoggerLevel.ERROR);

	}

	public final void clear() {
		panel.clear();
	}

	/**
	 * @param forceLevel
	 *            the forceLevel to set
	 */
	public void setForceLevel(LoggerLevel forceLevel) {
		panel.setForceLevel(forceLevel);
	}
}
