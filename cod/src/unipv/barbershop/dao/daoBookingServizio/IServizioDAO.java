package unipv.barbershop.dao.daoBookingServizio;
import java.util.ArrayList;
import unipv.barbershop.model.booking.Servizio;

public interface IServizioDAO {
	// Salva un nuovo tipo di servizio (es. Taglio Capelli) nel database
		boolean inserisciServizio(Servizio servizio);
		
		// Recupera il "Listino Prezzi" completo
		ArrayList<Servizio> recuperaTuttiIServizi();
}
