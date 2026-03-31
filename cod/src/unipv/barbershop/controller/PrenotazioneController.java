package unipv.barbershop.controller;
import unipv.barbershop.facade.BarbershopFacade;
import unipv.barbershop.model.booking.Prenotazione;
import unipv.barbershop.model.booking.Servizio;
import unipv.barbershop.model.user.Cliente;
import unipv.barbershop.model.user.Utente;
import unipv.barbershop.model.staff.Barbiere;
import java.time.LocalDateTime;
import java.util.List;
public class PrenotazioneController {
	private BarbershopFacade facade;

    public PrenotazioneController() {
        this.facade = BarbershopFacade.getInstance();
    }

    /**
     * @param barbiere Il barbiere selezionato
     * @param dataOra La data e l'ora
     * @param serviziSelezionati La lista dei servizi spuntati dall'utente 
     */
    public boolean creaPrenotazione(Barbiere barbiere, LocalDateTime dataOra, List<Servizio> serviziSelezionati) {
        
        // 1. VALIDAZIONE INPUT
        if (barbiere == null || dataOra == null || serviziSelezionati == null || serviziSelezionati.isEmpty()) {
            System.out.println("Errore di validazione: Compilare tutti i campi e selezionare almeno un servizio.");
            return false;
        }

        if (dataOra.isBefore(LocalDateTime.now())) {
            System.out.println("Errore: Impossibile prenotare nel passato.");
            return false;
        }

        // 2. RECUPERO UTENTE LOGGATO
        Utente utenteLoggato = facade.getLoggedUser();
        if (utenteLoggato == null || !utenteLoggato.getRuolo().equals("CLIENTE")) {
            System.out.println("Errore: Solo i clienti loggati possono prenotare.");
            return false;
        }

        try {
            // 3. CREAZIONE DELL'OGGETTO (usa il costruttore base)
            Cliente cliente = (Cliente) utenteLoggato;
            Prenotazione nuovaPrenotazione = new Prenotazione(cliente, barbiere, dataOra);

            // 4. INSERIMENTO DEI SERVIZI (Usando il setter esatto!)
            nuovaPrenotazione.setServiziScelti(serviziSelezionati);

            // 5. DELEGA ALLA FACADE
            return facade.effettuaPrenotazione(nuovaPrenotazione);

        } catch (RuntimeException e) {
            System.out.println("Operazione annullata: " + e.getMessage());
            return false;
        }
    }
}
