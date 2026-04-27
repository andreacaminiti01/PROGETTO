package unipv.barbershop.controller;
import unipv.barbershop.facade.BarbershopFacade;
import unipv.barbershop.view.admin.FinestraVisualizzaPrenotazioni;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
public class PrenotazioniAdminController {
	private FinestraVisualizzaPrenotazioni vista;
	private BarbershopFacade facade;

	public PrenotazioniAdminController(FinestraVisualizzaPrenotazioni vista) {
		this.vista = vista;
		this.facade = BarbershopFacade.getInstance();

		// 1. Colleghiamo i bottoni alle azioni
		inizializzaEventi();

		// 2. Riempiamo la tabella appena il controller viene creato
		aggiornaTabella(); 
	}

	private void inizializzaEventi() {
		// Configurazione del "Sorter" per permettere il filtraggio della tabella
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(vista.getTableModel());
        vista.getTabellaPrenotazioni().setRowSorter(sorter);

        // Gestione della ricerca in tempo reale
        vista.getTxtRicerca().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { filtra(); }
            @Override
            public void removeUpdate(DocumentEvent e) { filtra(); }
            @Override
            public void changedUpdate(DocumentEvent e) { filtra(); }

            private void filtra() {
                String testo = vista.getTxtRicerca().getText();
                if (testo.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    // Cerca il testo ignorando maiuscole e minuscole (?i)
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + testo));
                }
            }
           
		});
        vista.getbtnIndietro().addActionListener(e -> {
            vista.dispose(); 
        });
	}

	private void aggiornaTabella() {
		// Recuperiamo la lista di prenotazioni dal database tramite la Facade
		List<String[]> datiPrenotazioni = facade.getElencoPrenotazioniAdmin();

		// Prendiamo il "modello" della tabella
		DefaultTableModel model = vista.getTableModel();

		// Puliamo la tabella e inseriamo le righe aggiornate
		model.setRowCount(0); 
		for (String[] riga : datiPrenotazioni) {
			model.addRow(riga);
		}
	}
}
