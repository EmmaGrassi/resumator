package io.sytac.resumator.exception;

/**
 * Defines the main exception will be thrown in case.
 *
 * @author Selman Tayyar
 * @since 0.1
 */

public class ResumatorException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 82670320161310651L;

	public ResumatorException() {
		super();
	}

	public ResumatorException(String message) {
		super(message);

	}

	public ResumatorException(String message, Throwable cause) {
		super(message, cause);

	}

	@Override
	public String toString() {
		return super.toString();
	}

	@Override
	public String getMessage() {
		return "Resumator "+super.getMessage() ;
	}

}
