package jps.hyperspin.common.log;

/**
 * 
 * @author JPS
 * 
 */
public interface Logger {

	/**
	 * 
	 * @param message
	 *            message
	 */
	void info(final String message);

	/**
	 * 
	 * @param message
	 *            message
	 */
	void error(final String message);

	/**
	 * 
	 * @param message
	 *            message
	 */
	void trace(final String message);

	/**
	 * 
	 * @param message
	 *            message
	 */
	void notif(final String message);

	/**
	 * 
	 */
	void clear();
}
