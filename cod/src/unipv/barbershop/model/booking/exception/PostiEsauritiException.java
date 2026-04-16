package unipv.barbershop.model.booking.exception;

public class PostiEsauritiException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PostiEsauritiException() {
		super("Impossibile prenotare: il barbiere selezionato è già occupato.");
	}

}
