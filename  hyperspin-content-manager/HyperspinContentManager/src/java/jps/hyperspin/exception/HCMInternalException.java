package jps.hyperspin.exception;

/**
 * 
 * @author JPS
 * 
 */
public final class HCMInternalException extends HCMDatabaseException {

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
	public HCMInternalException(final Throwable cause, final String... messages) {
		super(cause, messages);
	}
}
