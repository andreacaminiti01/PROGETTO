package unipv.barbershop.controller;
import unipv.barbershop.facade.BarbershopFacade;
import unipv.barbershop.model.inventory.Prodotto;
import unipv.barbershop.model.inventory.exception.*;
import unipv.barbershop.model.inventory.exception.ScortaInsufficienteException;
import unipv.barbershop.view.admin.FinestraMagazzino;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
public class MagazzinoController {
	private BarbershopFacade facade;
	private FinestraMagazzino vista;
	private List<Prodotto> inventarioAttuale; // Cache per evitare continue chiamate al DB

	public MagazzinoController(FinestraMagazzino vista) {
		this.facade = BarbershopFacade.getInstance();
		this.vista = vista;

		ricaricaTabella(); // Carica i dati appena si apre la finestra
		inizializzaEventi();
	}

	private void inizializzaEventi() {
		// TASTO RIFORNISCI (+)
		vista.getBtnRifornisci().addActionListener(e -> gestisciOperazioneMagazzino(true));

		// TASTO CONSUMA (-)
		vista.getBtnConsuma().addActionListener(e -> gestisciOperazioneMagazzino(false));

		// TASTO INDIETRO
		vista.getBtnTornaIndietro().addActionListener(e -> vista.dispose());

		vista.getTabellaProdotti().getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				int rigaSelezionata = vista.getTabellaProdotti().getSelectedRow();
				if (rigaSelezionata != -1) {
					// Prende l'ID dalla prima colonna (indice 0) della riga cliccata
					String id = vista.getTabellaProdotti().getValueAt(rigaSelezionata, 0).toString();
					vista.setIdProdottoText(id);
				}
			}
		});

		vista.getBtnAggiungiNuovo().addActionListener(e -> {
			try {
				String nome = vista.getNomeProdottoText().trim();
				String qtaStr = vista.getQuantitaText().trim();

				if (nome.isEmpty() || qtaStr.isEmpty()) {
					JOptionPane.showMessageDialog(vista, "Errore: Inserisci il Nome e la Quantità iniziale!");
					return;
				}

				int quantita = Integer.parseInt(qtaStr);

				// Creazione dell'oggetto Prodotto a oggetti
				Prodotto nuovo = new Prodotto(nome, quantita);

				// Salvataggio tramite Facade
				if (facade.aggiungiNuovoProdotto(nuovo)) {
					JOptionPane.showMessageDialog(vista, "Prodotto '" + nome + "' inserito con successo!");
					vista.svuotaCampiTesto();
					ricaricaTabella(); // Aggiorna la JTable con i dati freschi dal DB
				} else {
					JOptionPane.showMessageDialog(vista, "Errore durante l'inserimento nel database.");
				}
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(vista, "La quantità deve essere un numero valido!");
			}
		});
	}

	private void gestisciOperazioneMagazzino(boolean isRifornimento) {
		try {
			// 1. Lettura e validazione input (Evita crash se l'utente scrive lettere!)
			String strId = vista.getIdProdottoText().trim();
			String strQta = vista.getQuantitaText().trim();

			if (strId.isEmpty() || strQta.isEmpty()) {
				JOptionPane.showMessageDialog(vista, "Compila ID e Quantità!", "Errore", JOptionPane.WARNING_MESSAGE);
				return;
			}

			int idProdotto = Integer.parseInt(strId);
			int quantita = Integer.parseInt(strQta);

			// 2. Cerchiamo il prodotto nella lista
			Prodotto prodottoSelezionato = inventarioAttuale.stream()
					.filter(p -> p.getId() == idProdotto)
					.findFirst()
					.orElse(null);

			if (prodottoSelezionato == null) {
				JOptionPane.showMessageDialog(vista, "Prodotto non trovato!", "Errore", JOptionPane.ERROR_MESSAGE);
				return;
			}

			// 3. Esecuzione dell'operazione usando la tua logica a oggetti
			boolean successo = false;
			if (isRifornimento) {
				prodottoSelezionato.setQuantitaInScorta(prodottoSelezionato.getQuantitaInScorta() + quantita);
				successo = facade.aggiornaProdotto(prodottoSelezionato);
			} else {
				prodottoSelezionato.riduciScorta(quantita); // Questo lancia l'eccezione se le scorte sono poche!
				successo = facade.aggiornaProdotto(prodottoSelezionato);
			}

			// 4. Feedback e aggiornamento
			if (successo) {
				JOptionPane.showMessageDialog(vista, "Operazione completata con successo!");
				vista.svuotaCampiTesto();
				ricaricaTabella(); // Ricarica i dati freschi dal DB

				if (!isRifornimento && prodottoSelezionato.isEsaurito()) {
					JOptionPane.showMessageDialog(vista, "ATTENZIONE: Il prodotto " + prodottoSelezionato.getNome() + " è esaurito!", "Allarme Scorte", JOptionPane.WARNING_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(vista, "Errore durante il salvataggio nel DB.", "Errore", JOptionPane.ERROR_MESSAGE);
			}

		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(vista, "Inserisci numeri validi, non lettere!", "Errore formato", JOptionPane.ERROR_MESSAGE);
		} catch (ScortaInsufficienteException | NegativeValueException ex) {
			// Cattura le TUE eccezioni personalizzate e ne mostra il messaggio!
			JOptionPane.showMessageDialog(vista, ex.getMessage(), "Operazione negata", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void ricaricaTabella() {
		inventarioAttuale = facade.getTuttiIProdotti();
		DefaultTableModel model = vista.getTableModel();
		model.setRowCount(0); // Svuota la tabella vecchia

		for (Prodotto p : inventarioAttuale) {
			Object[] riga = {p.getId(), p.getNome(), p.getQuantitaInScorta()};
			model.addRow(riga);
		}
	}
}
