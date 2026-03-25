package unipv.barbershop.dao.daoUtente;
import unipv.barbershop.model.user.Utente;
import java.util.List;


public interface IUtenteDAO {
	// Metodo per salvare un utente (Cliente o Admin) nel database
    void inserisciUtente(Utente utente);
    
    // Metodo per il login (Restituisce un Cliente o un Admin a seconda di chi entra)
    Utente login(String email, String password);
    
    // Metodo per recuperare la lista di tutti gli utenti registrati
    List<Utente> leggiTuttiGliUtenti();
}
