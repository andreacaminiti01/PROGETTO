package unipv.barbershop.facade;
import unipv.barbershop.dao.daoUtente.*;
import unipv.barbershop.dao.daoPrenotazione.*;
import unipv.barbershop.dao.daoBookingServizio.*;
import unipv.barbershop.dao.daoFeedback.FeedbackDAO;
import unipv.barbershop.dao.daoFeedback.IFeedbackDAO;
import unipv.barbershop.model.user.*;
import unipv.barbershop.model.booking.*;
import unipv.barbershop.model.booking.exception.PostiEsauritiException;
import unipv.barbershop.model.feedback.Feedback;

import java.util.List;
import unipv.barbershop.dao.daoProdotto.*;
import unipv.barbershop.model.inventory.*;
import unipv.barbershop.model.staff.Barbiere;
public class BarbershopFacade {
	/**
	 * Facade unificata per il sistema Barbershop.
	 * Implementa il pattern Singleton e fornisce un'interfaccia di alto livello.
	 */

	private static BarbershopFacade instance;

	// Stato della Sessione
	private Utente loggedUser;
	private boolean isLoggedIn;

	// Riferimenti ai DAO (Sottosistemi nascosti)
	private IUtenteDAO utenteDAO;
	private IPrenotazioneDAO prenotazioneDAO;
	private IServizioDAO servizioDAO;
	private IProdottoDAO prodottoDAO;
	private IFeedbackDAO feedbackDAO;

	// Costruttore privato (Singleton)
	private BarbershopFacade() {
		this.prodottoDAO = new ProdottoDAO();
		this.utenteDAO = new UtenteDAO();
		this.prenotazioneDAO = new PrenotazioneDAO();
		this.servizioDAO = new ServizioDAO();
		this.isLoggedIn = false;
		this.loggedUser = null;
		this.feedbackDAO= new FeedbackDAO();
	}

	public static BarbershopFacade getInstance() {
		if (instance == null) {
			instance = new BarbershopFacade();
		}
		return instance;
	}

	//AREA SESSIONE & UTENTE

	public boolean login(String email, String password) {
		try {
			this.loggedUser = utenteDAO.login(email, password);
			this.isLoggedIn = (loggedUser != null);
			return isLoggedIn;
		} catch (Exception e) {
			this.isLoggedIn = false;
			this.loggedUser = null;
			return false;
		}
	}

	public void logout() {
		this.isLoggedIn = false;
		this.loggedUser = null;
	}

	public Utente getLoggedUser() {
		return this.loggedUser;
	}

	public boolean isLogged() {
		return this.isLoggedIn;
	}

	//AREA OPERATIVA (DELEGA AI DAO)

	public List<Servizio> getTuttiIServizi() {
		// Nasconde la complessità della chiamata al DB
		return servizioDAO.recuperaTuttiIServizi();
	}

	public boolean effettuaPrenotazione(Prenotazione p) {
		// La Facade coordina l'operazione di business
		return prenotazioneDAO.salvaPrenotazione(p);
	}

	public void registraUtente(Utente u) {
		utenteDAO.inserisciUtente(u);
	}
	/**
	 * Recupera tutti i prodotti presenti in inventario.
	 */
	public List<Prodotto> getTuttiIProdotti() {
		return prodottoDAO.recuperaTuttiIProdotti();
	}

	/**
	 * Aggiorna lo stato di un prodotto (scorte/nome) nel database.
	 */
	public boolean aggiornaProdotto(Prodotto p) {
		/*La Facade fa da ponte. Prende l'oggetto Prodotto
 			ed estrae l'ID e la quantità per passarli al metodo aggiornaScorta!*/
		return prodottoDAO.aggiornaScorta(p.getId(), p.getQuantitaInScorta());
	}

	public List<Barbiere> getBarbieriDisponibili() {
	    // Chiama il DAO dei barbieri (o UtenteDAO filtrando per ruolo)
	    return utenteDAO.getTuttiIBarbieri(); 
	}

	public List<Servizio> getServiziOfferti() {
	    // Chiama il ServizioDAO per avere i prezzi e i nomi
	    return servizioDAO.recuperaTuttiIServizi();
	}

	public boolean prenota(Prenotazione p) throws PostiEsauritiException {
	    return prenotazioneDAO.salvaPrenotazione(p); 
	}
	
	public boolean inviaFeedback(Feedback f) {
	    return feedbackDAO.salvaFeedback(f);
	}
	
	public List<Prenotazione> getStoricoDettagliato() {
	    // Recuperiamo il cliente loggato per sapere di chi cercare le prenotazioni
	    Cliente c = (Cliente) this.getLoggedUser(); 
	    // Chiamiamo il metodo del DAO che abbiamo aggiunto prima
	    return prenotazioneDAO.recuperaPrenotazioniPerCliente(c.getId());
	}

	public List<String[]> getElencoPrenotazioniAdmin() {
		return prenotazioneDAO.getElencoPrenotazioni();
	
	}
	
	public List<Feedback> getTuttiIFeedback() {
	    // Delega la chiamata al DAO dei feedback
	    return feedbackDAO.recuperaTuttiIFeedback();
	}
	
	public boolean aggiungiNuovoProdotto(Prodotto p) {
	    // Chiama il metodo del DAO che esegue la INSERT INTO prodotti
	    return prodottoDAO.inserisciProdotto(p);
	}
}
