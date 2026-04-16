package unipv.barbershop.controller;
import unipv.barbershop.facade.BarbershopFacade;
import unipv.barbershop.model.booking.Prenotazione;
import unipv.barbershop.model.booking.Servizio;
import unipv.barbershop.model.booking.exception.PostiEsauritiException;
import unipv.barbershop.model.feedback.Feedback;
import unipv.barbershop.model.staff.Barbiere;
import unipv.barbershop.model.user.Cliente;
import unipv.barbershop.view.cliente.DashboardCliente;
import unipv.barbershop.view.prenotazione.DashboardNewPrenotazione;
import java.time.LocalDateTime;
import java.util.List;
import javax.swing.JOptionPane;
public class ClienteController {
	private DashboardCliente view;
	private BarbershopFacade facade;

	public ClienteController(DashboardCliente view) {
		this.view = view;
		this.facade = BarbershopFacade.getInstance();
		inizializzaEventi();
	}

	private void inizializzaEventi() {

		// --- 1. GESTIONE NUOVA PRENOTAZIONE ---
		view.getBtnPrenota().addActionListener(e -> {
			// Recuperiamo i dati reali per popolare la finestra
			List<Barbiere> barbieri = facade.getBarbieriDisponibili();
			List<Servizio> servizi = facade.getServiziOfferti();

			// Apriamo la vera finestra della prenotazione
			DashboardNewPrenotazione fp = new DashboardNewPrenotazione(barbieri, servizi);

			// Aggiungiamo la logica al tasto "Conferma" della finestrella
			fp.getBtnConferma().addActionListener(ev -> {
				gestisciSalvataggioPrenotazione(fp);
			});

			fp.setVisible(true);
		});

		// --- 2. BOTTONE I MIEI APPUNTAMENTI / FEEDBACK ---
		view.getBtnFeedback().addActionListener(e -> {
		    // 1. Recuperiamo lo storico reale tramite la Facade
		    List<Prenotazione> storico = facade.getStoricoDettagliato();
		    
		    // 2. Apriamo la finestra dello storico (FinestraStorico)
		    unipv.barbershop.view.cliente.storico.Storico fs = new unipv.barbershop.view.cliente.storico.Storico(storico);
		    
		    // 3. Colleghiamo la logica del feedback alla tabella dello storico
		    fs.getBtnLasciaFeedback().addActionListener(ev -> {
		        // Recuperiamo la prenotazione selezionata nella tabella
		        Prenotazione selezionata = fs.getPrenotazioneSelezionata();
		        if (selezionata != null) {
		            apriDialogoFeedback(selezionata);
		        } else {
		            JOptionPane.showMessageDialog(fs, "Seleziona una prenotazione dalla lista!");
		        }
		    });
		    
		    fs.setVisible(true);
		});
	}

	private void gestisciSalvataggioPrenotazione(DashboardNewPrenotazione fp) {
		try {
	        String dataTesto = fp.getTxtData().getText();
	        String oraTesto = (String) fp.getComboOre().getSelectedItem();
	        // Qui avviene il parse che può fallire
	        LocalDateTime dataOra = LocalDateTime.parse(dataTesto + "T" + oraTesto + ":00");

	        Barbiere b = (Barbiere) fp.getComboBarbiere().getSelectedItem();
	        List<Servizio> scelti = fp.getServiziSelezionati();

	        if (scelti.isEmpty()) {
	            JOptionPane.showMessageDialog(fp, "Seleziona almeno un servizio!");
	            return;
	        }

	        Cliente c = (Cliente) facade.getLoggedUser();
	        Prenotazione p = new Prenotazione(c, b, dataOra);
	        p.setServiziScelti(scelti);

	        if (facade.prenota(p)) {
	            JOptionPane.showMessageDialog(fp, "Prenotazione confermata!\nTotale: €" + p.calcolaPrezzoTotale());
	            fp.dispose();
	        }

	    } catch (java.time.format.DateTimeParseException ex) {
	        // Messaggio specifico per l'errore che hai avuto (mese 15)
	        JOptionPane.showMessageDialog(fp, "Data non valida! Usa il formato AAAA-MM-GG (es. 2026-04-16).", "Errore Data", JOptionPane.ERROR_MESSAGE);
	    } catch (PostiEsauritiException ex) {
	        JOptionPane.showMessageDialog(fp, "Orario occupato per questo barbiere!", "Errore", JOptionPane.ERROR_MESSAGE);
	    } catch (Exception ex) {
	        JOptionPane.showMessageDialog(fp, "Si è verificato un errore durante il salvataggio.");
	        ex.printStackTrace();
	    }
	}

	private void apriDialogoFeedback(unipv.barbershop.model.booking.Prenotazione p) {
		// 1. Chiediamo il voto tramite un semplice menu a tendina
		Integer[] voti = {1, 2, 3, 4, 5};
		Integer votoScelto = (Integer) JOptionPane.showInputDialog(
				null, "Quante stelle dai a questo servizio?", 
				"Lascia un Feedback", JOptionPane.QUESTION_MESSAGE, null, voti, 5);

		if (votoScelto != null) {
			// 2. Chiediamo un commento testuale
			String commento = JOptionPane.showInputDialog("Scrivi un breve commento:");

			// 3. Creiamo l'oggetto Feedback
			Cliente c = (Cliente) facade.getLoggedUser();
			Feedback f = new Feedback(c, p, votoScelto, commento);

			// 4. Invio al Database
			if (facade.inviaFeedback(f)) {
				JOptionPane.showMessageDialog(null, "Grazie! Feedback inviato con successo.");
			} else {
				JOptionPane.showMessageDialog(null, "Errore: hai già valutato questa prenotazione.", "Errore", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

}
