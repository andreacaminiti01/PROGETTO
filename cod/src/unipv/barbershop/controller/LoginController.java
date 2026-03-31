package unipv.barbershop.controller;
import unipv.barbershop.facade.BarbershopFacade;
import unipv.barbershop.model.user.Utente;

public class LoginController {
	// Il Controller ha bisogno di parlare con la Facade
    private BarbershopFacade facade;

    public LoginController() {
        // Otteniamo l'unica istanza disponibile (Singleton)
        this.facade = BarbershopFacade.getInstance();
    }

    /**
     * Metodo chiamato dall'interfaccia grafica quando l'utente clicca "Login"
     * @param email L'email inserita nel campo di testo
     * @param password La password inserita nel campo di testo
     * @return true se il login ha successo, false se fallisce
     */
    public boolean gestisciLogin(String email, String password) {
        
        // 1. Controllo base sugli input (evitiamo di disturbare il DB per nulla)
        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            System.out.println("Errore: Campi vuoti. Inserire email e password.");
            return false;
        }

        // 2. Deleghiamo il vero controllo alla Facade
        boolean successo = facade.login(email, password);

        // 3. Decidiamo cosa fare dopo
        if (successo) {
            Utente utenteLoggato = facade.getLoggedUser();
            System.out.println("Benvenuto, " + utenteLoggato.getNome() + "!");
            
            // POLIMORFISMO IN AZIONE: Controlliamo il ruolo per aprire la schermata giusta
            if (utenteLoggato.getRuolo().equals("AMMINISTRATORE")) {
                apriDashboardAdmin();
            } else {
                apriDashboardCliente();
            }
            return true;
        } else {
            System.out.println("Errore: Credenziali errate o utente non trovato.");
            return false;
        }
    }

    public void gestisciLogout() {
        facade.logout();
        System.out.println("Logout effettuato con successo.");
        
    }

    // --- Metodi fittizi con l'apertura delle vere finestre grafiche ---

    private void apriDashboardAdmin() {
        System.out.println("Apertura Pannello di Controllo Amministratore...");
        
    }

    private void apriDashboardCliente() {
        System.out.println("Apertura Area Riservata Cliente...");
        
    }
}
