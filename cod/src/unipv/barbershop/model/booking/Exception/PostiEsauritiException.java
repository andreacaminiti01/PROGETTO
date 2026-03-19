package unipv.barbershop.model.booking.Exception;

public class PostiEsauritiException extends RuntimeException {

	public PostiEsauritiException() {
		super("Impossibile prenotare: il barbiere selezionato è già occupato.");
	}

}
