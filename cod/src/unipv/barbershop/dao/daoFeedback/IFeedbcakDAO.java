package unipv.barbershop.dao.daoFeedback;
import java.util.List;
import unipv.barbershop.model.feedback.Feedback;

public interface IFeedbcakDAO {
	// Salva una nuova recensione nel database
	boolean salvaFeedback(Feedback f);

	// Recupera tutte le recensioni (utile per mostrarle agli altri clienti o all'admin)
	List<Feedback> recuperaTuttiIFeedback();
}
