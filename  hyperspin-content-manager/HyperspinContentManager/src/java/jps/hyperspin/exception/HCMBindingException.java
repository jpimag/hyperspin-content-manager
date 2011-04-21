package jps.hyperspin.exception;

/**
 * 
 * @author JPS
 * 
 */
public class HCMBindingException extends HCMDatabaseException {

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
	public HCMBindingException(final Throwable cause, final String... messages) {
		super(cause, messages);
	}

}
