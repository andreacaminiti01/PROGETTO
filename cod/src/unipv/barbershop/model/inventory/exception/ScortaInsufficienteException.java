package unipv.barbershop.model.inventory.exception;

public class ScortaInsufficienteException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ScortaInsufficienteException(String nomeProdotto) {
		super("Attenzione: scorte insufficienti per il prodotto '" + nomeProdotto + "'");
	}

}
