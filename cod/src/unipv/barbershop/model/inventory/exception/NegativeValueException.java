package unipv.barbershop.model.inventory.exception;

public class NegativeValueException extends RuntimeException {

	public NegativeValueException(String messaggio) {
		super(messaggio);
	}
	
}
