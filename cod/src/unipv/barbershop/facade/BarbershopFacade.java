package unipv.barbershop.facade;
import unipv.barbershop.dao.daoUtente.*;
import unipv.barbershop.dao.daoPrenotazione.*;
import unipv.barbershop.dao.daoBookingServizio.*;
import unipv.barbershop.model.user.*;
import unipv.barbershop.model.booking.*;
import java.util.List;

public class BarbershopFacade {
	/**
	 * Facade unificata per il sistema Barbershop.
	 * Implementa il pattern Singleton e fornisce un'interfaccia di alto livello.
	 */

	private static BarbershopFacade instance;

	// Stato della Sessione (come in ConcreteSessionFacade)
	private Utente loggedUser;
	private boolean isLoggedIn;

	// Riferimenti ai DAO (Sottosistemi nascosti)
	private IUtenteDAO utenteDAO;
	private IPrenotazioneDAO prenotazioneDAO;
	private IServizioDAO servizioDAO;

	// Costruttore privato (Singleton)
	private BarbershopFacade() {
		this.utenteDAO = new UtenteDAO();
		this.prenotazioneDAO = new PrenotazioneDAO();
		this.servizioDAO = new ServizioDAO();
		this.isLoggedIn = false;
		this.loggedUser = null;
	}

	public static BarbershopFacade getInstance() {
		if (instance == null) {
			instance = new BarbershopFacade();
		}
		return instance;
	}

	// --- AREA SESSIONE & UTENTE ---

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

	// --- AREA OPERATIVA (DELEGA AI DAO) ---

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



}
