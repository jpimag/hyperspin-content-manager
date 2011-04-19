package jps.hyperspin.exception;

/**
 * 
 * @author JPS
 * 
 */
public class HSDNotExistingFileOrDirException extends HyperSpinDatabaseException {

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
	public HSDNotExistingFileOrDirException(final Throwable cause, final String... messages) {
		super(cause, messages);
	}

}
