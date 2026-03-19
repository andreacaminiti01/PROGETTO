package unipv.barbershop.model.inventory.Exception;

public class ScortaInsufficienteException extends RuntimeException {

	public ScortaInsufficienteException(String nomeProdotto) {
		super("Attenzione: scorte insufficienti per il prodotto '" + nomeProdotto + "'");
	}

}
