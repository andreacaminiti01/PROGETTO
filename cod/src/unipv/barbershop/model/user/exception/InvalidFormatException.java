package unipv.barbershop.model.user.exception;

public class InvalidFormatException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidFormatException(String messaggio) {
        super(messaggio);
    }
}
