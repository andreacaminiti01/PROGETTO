package unipv.barbershop.view.cliente.storico;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import unipv.barbershop.model.booking.Prenotazione;
import javax.swing.JFrame;

public class Storico extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable tabella;
    private JButton btnLasciaFeedback;
    private List<Prenotazione> listaDati;

    public Storico(List<Prenotazione> prenotazioni) {
        this.listaDati = prenotazioni;
        setTitle("I Miei Appuntamenti");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Configurazione Tabella
        String[] colonne = {"ID", "Data e Ora", "Barbiere"};
        DefaultTableModel model = new DefaultTableModel(colonne, 0);
        
        for (Prenotazione p : prenotazioni) {
            Object[] riga = {
                p.getId(),
                p.getDataOra().toString().replace("T", " "),
                p.getBarbiere().getNome() + " " + p.getBarbiere().getCognome()
            };
            model.addRow(riga);
        }

        tabella = new JTable(model);
        add(new JScrollPane(tabella), BorderLayout.CENTER);

        btnLasciaFeedback = new JButton("Lascia Feedback");
        add(btnLasciaFeedback, BorderLayout.SOUTH);
    }

    // Metodo fondamentale per il Controller
    public Prenotazione getPrenotazioneSelezionata() {
        int riga = tabella.getSelectedRow();
        if (riga != -1) {
            return listaDati.get(riga);
        }
        return null;
    }

    public JButton getBtnLasciaFeedback() {
        return btnLasciaFeedback;
    }
}
