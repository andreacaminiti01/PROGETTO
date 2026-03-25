package unipv.barbershop.model.user.exception;

public class EmptyFieldException extends RuntimeException {
	public EmptyFieldException(String campo) {
        super("Il campo " + campo + " non può essere vuoto.");
    }
}
