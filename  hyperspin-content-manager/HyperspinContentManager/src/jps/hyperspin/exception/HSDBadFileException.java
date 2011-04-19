package jps.hyperspin.exception;

/**
 * 
 * @author JPS
 * 
 */
public class HSDBadFileException extends HyperSpinDatabaseException {

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
	public HSDBadFileException(final Throwable cause, final String... messages) {
		super(cause, messages);
	}

}
