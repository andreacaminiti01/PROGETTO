package unipv.barbershop.controller;
import unipv.barbershop.facade.BarbershopFacade;
import unipv.barbershop.model.inventory.Prodotto;
import unipv.barbershop.model.inventory.exception.*;
import unipv.barbershop.model.inventory.exception.ScortaInsufficienteException;
import unipv.barbershop.model.user.Utente;

import java.util.List;
public class MagazzinoController {
	private BarbershopFacade facade;

	public MagazzinoController() {
        this.facade = BarbershopFacade.getInstance();
    }

    /**
     * Metodo per la Vista: fornisce la lista dei prodotti per riempire la tabella.
     */
    public List<Prodotto> getInventario() {
        if (!isAdminLoggato()) {
            System.out.println("Accesso negato: Solo gli amministratori possono visualizzare il magazzino.");
            return null;
        }
        return facade.getTuttiIProdotti(); 
    }

    /**
     * Metodo per aggiungere nuove scorte (es. arrivo corriere).
     */
    public boolean rifornisciProdotto(Prodotto prodotto, int quantitaDaAggiungere) {
        if (!isAdminLoggato()) return false;

        if (prodotto == null || quantitaDaAggiungere <= 0) {
            System.out.println("Errore: Selezionare un prodotto e inserire una quantità da aggiungere valida.");
            return false;
        }

        try {
            // Calcoliamo la nuova scorta e usiamo il tuo setter blindato
            int nuovaQuantita = prodotto.getQuantitaInScorta() + quantitaDaAggiungere;
            prodotto.setQuantitaInScorta(nuovaQuantita);
            
            // Salviamo nel Database tramite Facade
            return facade.aggiornaProdotto(prodotto);

        } catch (NegativeValueException e) {
            // Questa intercetta il controllo che hai messo nel setQuantitaInScorta
            System.out.println("Errore di validazione: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Errore di sistema durante il rifornimento.");
            return false;
        }
    }

    /**
     * Metodo per registrare il consumo di un prodotto.
     */
    public boolean consumaProdotto(Prodotto prodotto, int quantitaUsata) {
        if (!isAdminLoggato()) return false;

        if (prodotto == null || quantitaUsata <= 0) {
            System.out.println("Errore: Inserire una quantità valida da consumare.");
            return false;
        }

        try {
            // Chiamiamo il tuo metodo esatto: se la scorta è poca, esplode la tua eccezione personalizzata!
            prodotto.riduciScorta(quantitaUsata);

            // Se arriviamo qui, la scorta era sufficiente. Aggiorniamo il DB.
            boolean successo = facade.aggiornaProdotto(prodotto);
            
            if (successo) {
                System.out.println("Consumo registrato. Nuova scorta: " + prodotto.getQuantitaInScorta());
                
                // Sfruttiamo il tuo metodo isEsaurito per un bel popup di avviso per l'Admin!
                if (prodotto.isEsaurito()) {
                    System.out.println("ALERT MAGAZZINO: Il prodotto " + prodotto.getNome() + " è appena terminato! Rifornire al più presto.");
                }
            }
            return successo;

        } catch (ScortaInsufficienteException e) {
            // La grafica di Fabio stamperà direttamente il messaggio che hai scritto nella classe dell'eccezione
            System.out.println(e.getMessage()); 
            return false;
            
        } catch (Exception e) {
            System.out.println("Errore di connessione al database.");
            return false;
        }
    }

    // --- Metodo di utilità interno (Private) ---
    private boolean isAdminLoggato() {
        Utente utenteLoggato = facade.getLoggedUser();
        return utenteLoggato != null && utenteLoggato.getRuolo().equals("AMMINISTRATORE");
    }
}
