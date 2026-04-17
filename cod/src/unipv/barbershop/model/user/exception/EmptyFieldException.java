package unipv.barbershop.model.user.exception;

public class EmptyFieldException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmptyFieldException(String campo) {
        super("Il campo " + campo + " non può essere vuoto.");
    }
}
