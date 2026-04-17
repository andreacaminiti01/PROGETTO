package unipv.barbershop.model.inventory.exception;

public class NegativeValueException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NegativeValueException(String messaggio) {
		super(messaggio);
	}
	
}
