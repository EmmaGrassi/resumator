package io.sytac.resumator.exception;

/**
 * Defines the internal exception will be thrown in case.This would mainy refer to code bugs and will return http 500 to the client.
 *
 * @author Selman Tayyar
 * @since 0.1
 */

public class ResumatorInternalException extends ResumatorException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3088161627612339392L;

	public ResumatorInternalException() {
		super();
	}

	public ResumatorInternalException(String message) {
		super(message);

	}

	public ResumatorInternalException(String message, Throwable cause) {
		super(message, cause);

	}

	@Override
	public String toString() {
		return super.toString();
	}

	@Override
	public String getMessage() {
		return "Internal Exception exception "+super.getMessage();
	}
}
