package io.sytac.resumator.exception;

/**
 * Defines the  exception occured due to user input will be thrown in case.
 * This would  refer to user input error and will return http 400 to the client.
 *
 * @author Selman Tayyar
 * @since 0.1
 */

public class ResumatorUserInputException extends ResumatorException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4698491354051738791L;

	public ResumatorUserInputException() {
		super();
	}

	public ResumatorUserInputException(String message) {
		super(message);

	}

	public ResumatorUserInputException(String message, Throwable cause) {
		super(message, cause);

	}

	@Override
	public String toString() {
		return super.toString();
	}

	@Override
	public String getMessage() {
		return "User Input exception "+super.getMessage();
	}
}
