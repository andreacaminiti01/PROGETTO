package unipv.barbershop.view.admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FinestraVisualizzaPrenotazioni extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTable tabellaPrenotazioni;
	private DefaultTableModel model;
	private JButton btnIndietro;
	private JTextField txtRicerca; 

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
		JPanel panelSud = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));

		panelSud.add(new JLabel("🔍 Filtra Risultati:"));
		txtRicerca = new JTextField(20); // Spazio per scrivere
		panelSud.add(txtRicerca);

		btnIndietro = new JButton("Chiudi");
		panelSud.add(btnIndietro);

		add(panelSud, BorderLayout.SOUTH);
	}

	// Getter per il controller

	public JButton getbtnIndietro() { return btnIndietro; }
	public DefaultTableModel getTableModel() { return model; }
	public JTable getTabellaPrenotazioni() { return tabellaPrenotazioni; }
	public JTextField getTxtRicerca() { return txtRicerca; }
}


