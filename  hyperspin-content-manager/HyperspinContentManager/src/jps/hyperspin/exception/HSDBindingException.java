package jps.hyperspin.exception;

/**
 * 
 * @author JPS
 * 
 */
public class HSDBindingException extends HyperSpinDatabaseException {

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
	public HSDBindingException(final Throwable cause, final String... messages) {
		super(cause, messages);
	}

}
