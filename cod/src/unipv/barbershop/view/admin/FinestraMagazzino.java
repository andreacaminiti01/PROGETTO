package unipv.barbershop.view.admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


public class FinestraMagazzino extends JFrame {

	private static final long serialVersionUID = 1L;
	//Componenti della finestra
	private JTable tabellaProdotti;
	private DefaultTableModel tableModel;
	private JButton btnRifornisci;
	private JButton btnConsuma;
	private JButton btnTornaIndietro;
	private JButton btnAggiungiNuovo;

	//campi per input rapido
	private JTextField txtIdProdotto;
	private JTextField txtQuantita;
	private JTextField txtNomeProdotto;

	public FinestraMagazzino() {
		setTitle("Gestione Inventario Magazzino");
		setSize(700, 500);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //per chiudere solo la finestra
		setLocationRelativeTo(null);
		setLayout(new BorderLayout(10, 10));

		//TITOLO IN ALTO
		JLabel lblTitolo = new JLabel("Inventario Prodot(neti", SwingConstants.CENTER);
		lblTitolo.setFont(new Font("Arial", Font.BOLD, 20));
		add(lblTitolo, BorderLayout.NORTH);


		//TABELLA CENTRALE
		//colonne della tabella
		String[] colonne = {"ID Prodotto", "Nome Prodotto" , "Quantita in Scorta"};
		tableModel = new DefaultTableModel(colonne, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Rende la tabella non modificabile a mano cliccandoci sopra
			}

		};
		tabellaProdotti = new JTable(tableModel);
		tabellaProdotti.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Si può selezionare una riga alla volta

		//Mettiamo la tabella in uno ScrollPane (così appare la barra di scorrimento se ci sono tanti prodotti)
		JScrollPane scrollPane = new JScrollPane(tabellaProdotti);
		add(scrollPane, BorderLayout.CENTER);

		//PANNELLA INFERIORE (Input e Bottoni)

		JPanel panelSudCompleto = new JPanel(new GridLayout(2, 1, 5, 5));
		panelSudCompleto.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

		// 2. PRIMA RIGA: Solo gli input di testo
		JPanel panelInput = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));

		panelInput.add(new JLabel("ID Prodotto:"));
		txtIdProdotto = new JTextField(5);
		panelInput.add(txtIdProdotto);

		panelInput.add(new JLabel("Quantità:"));
		txtQuantita = new JTextField(5);
		panelInput.add(txtQuantita);

		panelInput.add(new JLabel("Nome:"));
		txtNomeProdotto = new JTextField(12);
		panelInput.add(txtNomeProdotto);

		// 3. SECONDA RIGA: Solo i bottoni
		JPanel panelBottoni = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));

		btnAggiungiNuovo = new JButton("Aggiungi Nuovo");
		btnRifornisci = new JButton("Rifornisci (+)");
		btnConsuma = new JButton(" Consuma (-)");
		btnTornaIndietro = new JButton("Indietro");

		//i bottoni nell'ordine corretto
		panelBottoni.add(btnAggiungiNuovo);
		panelBottoni.add(btnRifornisci);
		panelBottoni.add(btnConsuma);
		panelBottoni.add(btnTornaIndietro);

		// 4. due righe nel pannello principale Sud
		panelSudCompleto.add(panelInput);
		panelSudCompleto.add(panelBottoni);

		// 5. Aggiungiamo il tutto alla finestra
		add(panelSudCompleto, BorderLayout.SOUTH);
	}
	//  METODI PER IL CONTROLLER

	// Getters per permettere al Controller di leggere i dati scritti e agganciare i click
	public JButton getBtnRifornisci() { return btnRifornisci; }
	public JButton getBtnConsuma() { return btnConsuma; }
	public JButton getBtnTornaIndietro() { return btnTornaIndietro; }
	public JButton getBtnAggiungiNuovo() { return btnAggiungiNuovo; }
	public String getNomeProdottoText() { return txtNomeProdotto.getText(); }
	public String getIdProdottoText() { return txtIdProdotto.getText(); }
	public String getQuantitaText() { return txtQuantita.getText(); }

	// Metodo fondamentale per svuotare e riempire la tabella con i dati dal DB!
	public DefaultTableModel getTableModel() { return tableModel; }
	public JTable getTabellaProdotti() { return tabellaProdotti; }

	public void svuotaCampiTesto() {
		txtIdProdotto.setText("");
		txtQuantita.setText("");
		txtNomeProdotto.setText("");
	}

	public void setIdProdottoText(String id) {
		txtIdProdotto.setText(id);
	}




}
