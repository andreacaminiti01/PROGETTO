package unipv.barbershop.dao.daoPrenotazione;
import unipv.barbershop.model.booking.Prenotazione;
import unipv.barbershop.model.booking.exception.PostiEsauritiException;
import java.time.LocalDateTime;
import java.util.List;

public interface IPrenotazioneDAO {
	
	// Controlla se il barbiere è libero a quell'ora esatta
	boolean isBarbiereDisponibile(int idBarbiere, LocalDateTime dataOra);

	// Salva l'appuntamento (Lancia l'eccezione se il posto è già occupato!)
	boolean salvaPrenotazione(Prenotazione p) throws PostiEsauritiException;

	public List<Prenotazione> recuperaPrenotazioniPerCliente(int idCliente);

	List<String[]> getElencoPrenotazioni();

	
}
