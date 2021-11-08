package geometri;

/**
 * An <code>Exception</code> which is thrown when trying to set an illegal
 * position. A position is illegal if any of its coordinates is negative.
 */
public class IllegalPositionException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * Construct a new <code>Exception</code> <i>without</i> a detail message.
	 */
	public IllegalPositionException() {
		super();
	}// constructor

	/**
	 * Construct a new <code>Exception</code> <i>with</i> a detail message.
	 *
	 * @param message The detail message .
	 */
	public IllegalPositionException(final String message) {
		super(message);
	}// constructor
}// IllegalPositionException
