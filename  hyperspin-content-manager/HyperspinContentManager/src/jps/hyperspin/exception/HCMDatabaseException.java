package jps.hyperspin.exception;

/**
 * 
 * @author JPS
 * 
 */
public class HCMDatabaseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String[] messages;

	/**
	 * 
	 * @param messages
	 *            message
	 * @param cause
	 *            cause
	 */
	public HCMDatabaseException(final Throwable cause,
			final String... messages) {
		super(cause);
		this.messages = messages;

	}

	/**
	 * @return the messages
	 */
	public final String[] getMessages() {
		return messages;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String toString() {
		String result = "[" + getClass().getName() + "]";
		for (String s : getMessages()) {
			result += "\n" + s;
		}
		if (getCause() != null) {
			result += "\nCause:\n";
			for (StackTraceElement element : getCause().getStackTrace()) {
				result += element.toString() + "\n";
			}
		}
		return result;

	}
}
