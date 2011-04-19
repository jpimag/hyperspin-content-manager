package jps.hyperspin.exception;

/**
 * 
 * @author JPS
 * 
 */
public final class HSDInternalException extends HyperSpinDatabaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param cause
	 *            cause
	 * @param messages
	 *            messages
	 */
	public HSDInternalException(final Throwable cause, final String... messages) {
		super(cause, messages);
	}
}
