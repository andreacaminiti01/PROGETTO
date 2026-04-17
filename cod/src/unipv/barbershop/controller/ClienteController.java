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
			List<Barbiere> barbieri = facade.getBarbieriDisponibili();
			List<Servizio> servizi = facade.getServiziOfferti();

			DashboardNewPrenotazione fp = new DashboardNewPrenotazione(barbieri, servizi);

			fp.getBtnConferma().addActionListener(ev -> {
				gestisciSalvataggioPrenotazione(fp);
			});

			fp.setVisible(true);
		});

		// --- 2. BOTTONE I MIEI APPUNTAMENTI / FEEDBACK ---
		view.getBtnFeedback().addActionListener(e -> {
		    List<Prenotazione> storico = facade.getStoricoDettagliato();
		    
		    unipv.barbershop.view.cliente.storico.Storico fs = new unipv.barbershop.view.cliente.storico.Storico(storico);
		    
		    fs.getBtnLasciaFeedback().addActionListener(ev -> {
		        Prenotazione selezionata = fs.getPrenotazioneSelezionata();
		        if (selezionata != null) {
		            apriDialogoFeedback(selezionata);
		        } else {
		            JOptionPane.showMessageDialog(fs, "Seleziona una prenotazione dalla lista!");
		        }
		    });
		    
		    fs.setVisible(true);
		});

		// --- 3. BOTTONE LOGOUT (Problema Risolto!) ---
		view.getBtnLogout().addActionListener(e -> {
			int conferma = JOptionPane.showConfirmDialog(view, 
				"Vuoi uscire dal tuo account?", "Conferma Logout", JOptionPane.YES_NO_OPTION);
				
			if (conferma == JOptionPane.YES_OPTION) {
				// 1. Pulisce la memoria
				facade.logout(); 
				// 2. Chiude la dashboard del cliente
				view.dispose(); 
				// 3. Riapre la pagina di Login
				new unipv.barbershop.view.account.FinestraLogin().setVisible(true); 
			}
		});
	}

	private void gestisciSalvataggioPrenotazione(DashboardNewPrenotazione fp) {
		try {
	        String dataTesto = fp.getTxtData().getText();
	        String oraTesto = (String) fp.getComboOre().getSelectedItem();
	        LocalDateTime dataOra = LocalDateTime.parse(dataTesto + "T" + oraTesto + ":00");

			// --- CONTROLLO VIAGGIO NEL TEMPO (Problema Risolto!) ---
			if (dataOra.isBefore(LocalDateTime.now())) {
				JOptionPane.showMessageDialog(fp, 
					"Errore: Non puoi prenotare un appuntamento in una data già passata!", 
					"Data non valida", 
					JOptionPane.ERROR_MESSAGE);
				return; // Interrompe subito e non salva nel DB
			}

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
	        JOptionPane.showMessageDialog(fp, "Data non valida! Usa il formato AAAA-MM-GG (es. 2026-04-16).", "Errore Data", JOptionPane.ERROR_MESSAGE);
	    } catch (PostiEsauritiException ex) {
	        JOptionPane.showMessageDialog(fp, "Orario occupato per questo barbiere!", "Errore", JOptionPane.ERROR_MESSAGE);
	    } catch (Exception ex) {
	        JOptionPane.showMessageDialog(fp, "Si è verificato un errore durante il salvataggio.");
	        ex.printStackTrace();
	    }
	}

	private void apriDialogoFeedback(unipv.barbershop.model.booking.Prenotazione p) {
		Integer[] voti = {1, 2, 3, 4, 5};
		Integer votoScelto = (Integer) JOptionPane.showInputDialog(
				null, "Quante stelle dai a questo servizio?", 
				"Lascia un Feedback", JOptionPane.QUESTION_MESSAGE, null, voti, 5);

		if (votoScelto != null) {
			String commento = JOptionPane.showInputDialog("Scrivi un breve commento:");
			Cliente c = (Cliente) facade.getLoggedUser();
			Feedback f = new Feedback(c, p, votoScelto, commento);

			if (facade.inviaFeedback(f)) {
				JOptionPane.showMessageDialog(null, "Grazie! Feedback inviato con successo.");
			} else {
				JOptionPane.showMessageDialog(null, "Errore: hai già valutato questa prenotazione.", "Errore", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}