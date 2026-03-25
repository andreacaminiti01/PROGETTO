package unipv.barbershop.model.booking.exception;

public class PostiEsauritiException extends RuntimeException {

	public PostiEsauritiException() {
		super("Impossibile prenotare: il barbiere selezionato è già occupato.");
	}

}
