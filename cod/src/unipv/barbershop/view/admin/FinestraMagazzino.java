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
	
	//campi per input rapido
	private JTextField txtIdProdotto;
	private JTextField txtQuantita;
	
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
	JPanel panelBottoni = new JPanel(new FlowLayout());
	
	panelBottoni.add(new JLabel("ID Prodotto:"));
	txtIdProdotto = new JTextField(5);
	panelBottoni.add(txtIdProdotto);
	
	panelBottoni.add(new JLabel("Quantità:"));
	txtQuantita = new JTextField(5);
	panelBottoni.add(txtQuantita);
	
	btnRifornisci = new JButton("🟢 Rifornisci (+)");
	btnConsuma = new JButton("🔴 Consuma (-)");
    btnTornaIndietro = new JButton("Indietro");
    
    panelBottoni.add(btnRifornisci);
    panelBottoni.add(btnConsuma);
    panelBottoni.add(btnTornaIndietro);
    
    add(panelBottoni, BorderLayout.SOUTH);
	}
// --- METODI PER IL CONTROLLER ---
    
    // Getters per permettere al Controller di leggere i dati scritti e agganciare i click
    public JButton getBtnRifornisci() { return btnRifornisci; }
    public JButton getBtnConsuma() { return btnConsuma; }
    public JButton getBtnTornaIndietro() { return btnTornaIndietro; }
    public String getIdProdottoText() { return txtIdProdotto.getText(); }
    public String getQuantitaText() { return txtQuantita.getText(); }
    
    // Metodo fondamentale per svuotare e riempire la tabella con i dati freschi dal DB!
    public DefaultTableModel getTableModel() { return tableModel; }
    public JTable getTabellaProdotti() { return tabellaProdotti; }
    
    public void svuotaCampiTesto() {
        txtIdProdotto.setText("");
        txtQuantita.setText("");
    }
	
	
	
	
	

}
