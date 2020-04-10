package exceptions;

public class InvalidResponseException extends Exception {

	private static final long serialVersionUID = -2707900796838648963L;

	public InvalidResponseException(String message) {
		super(message);
	}
}
