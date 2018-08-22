package serverhardwarecollector.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

//@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
@ResponseStatus(HttpStatus.NOT_FOUND)
public class IllegalApplicationStateException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IllegalApplicationStateException(String exception) {
		super(exception);
	}
}
