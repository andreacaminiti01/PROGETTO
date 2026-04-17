package unipv.barbershop.view.admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FinestraVisualizzaPrenotazioni extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTable tabellaPrenotazioni;
    private DefaultTableModel model;
    private JButton btnAggiorna;
    private JButton btnIndietro;

    public FinestraVisualizzaPrenotazioni() {
        setTitle("Agenda Prenotazioni Barbershop");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // 1. Titolo
        JLabel lblTitolo = new JLabel("Elenco Appuntamenti", SwingConstants.CENTER);
        lblTitolo.setFont(new Font("Arial", Font.BOLD, 20));
        add(lblTitolo, BorderLayout.NORTH);

        // 2. Tabella
        // Definiamo le colonne: Data, Cliente, Barbiere e Servizi scelti
        String[] colonne = {"Data e Ora", "Cliente", "Barbiere", "Servizi"};
        model = new DefaultTableModel(colonne, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabella di sola lettura
            }
        };
        
        tabellaPrenotazioni = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tabellaPrenotazioni);
        add(scrollPane, BorderLayout.CENTER);

        // 3. Bottoni
        JPanel panelBottoni = new JPanel(new FlowLayout());
        btnAggiorna = new JButton("Aggiorna Lista");
        btnIndietro = new JButton("Chiudi");
        
        panelBottoni.add(btnAggiorna);
        panelBottoni.add(btnIndietro);
        add(panelBottoni, BorderLayout.SOUTH);
    }

    // Getter per il controller
    public JButton getBtnAggiorna() { return btnAggiorna; }
    public JButton getBtnIndietro() { return btnIndietro; }
    public DefaultTableModel getTableModel() { return model; }
}


