package unipv.barbershop.model.user.exception;

public class CredenzialiErrateException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CredenzialiErrateException() {
		super("Errore: Email o Password non corretti");
	}
	
	
	
	
	
}
