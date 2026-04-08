package unipv.barbershop.view.account;
import unipv.barbershop.facade.BarbershopFacade;
import unipv.barbershop.model.user.Cliente;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class FinestraRegistrazione extends JFrame{
	private JTextField txtNome;
	private JTextField txtEmail;
	private JPasswordField txtPassword;
	private JButton btnRegistrati;
	private JButton btnAnnulla;

	public FinestraRegistrazione() {
		// --- 1. IMPOSTAZIONI FINESTRA ---
		setTitle("Barbershop - Nuova Registrazione");
		setSize(400, 450); // Un po' più alta del login
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);

		// --- 2. DESIGN E PADDING ---
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

		// Titolo
		JLabel lblTitolo = new JLabel("Crea Account");
		lblTitolo.setFont(new Font("Segoe UI", Font.BOLD, 24));
		lblTitolo.setAlignmentX(Component.CENTER_ALIGNMENT);

		mainPanel.add(lblTitolo);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

		// --- 3. CAMPI DI TESTO ---
		// Nome
		JLabel lblNome = new JLabel("Nome e Cognome:");
		lblNome.setAlignmentX(Component.CENTER_ALIGNMENT);
		txtNome = new JTextField(20);
		txtNome.setMaximumSize(new Dimension(300, 35));

		// Email
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setAlignmentX(Component.CENTER_ALIGNMENT);
		txtEmail = new JTextField(20);
		txtEmail.setMaximumSize(new Dimension(300, 35));

		// Password
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
		txtPassword = new JPasswordField(20);
		txtPassword.setMaximumSize(new Dimension(300, 35));

		// Aggiungiamo tutto al pannello
		mainPanel.add(lblNome);
		mainPanel.add(txtNome);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		mainPanel.add(lblEmail);
		mainPanel.add(txtEmail);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		mainPanel.add(lblPassword);
		mainPanel.add(txtPassword);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

		// --- 4. BOTTONI ---
		JPanel panelBottoni = new JPanel();
		panelBottoni.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 0)); // Bottoni affiancati

		btnRegistrati = new JButton("Registrati");
		btnRegistrati.putClientProperty("JButton.buttonType", "roundRect");

		btnAnnulla = new JButton("Torna al Login");
		btnAnnulla.putClientProperty("JButton.buttonType", "roundRect");

		panelBottoni.add(btnAnnulla);
		panelBottoni.add(btnRegistrati);

		mainPanel.add(panelBottoni);
		add(mainPanel);

		// --- 5. AZIONI DEI BOTTONI ---

		// Cosa succede se clicco "Registrati"?
		btnRegistrati.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 1. Controlliamo che i campi non siano vuoti
				if (txtNome.getText().isEmpty() || txtEmail.getText().isEmpty() || txtPassword.getPassword().length == 0) {
					JOptionPane.showMessageDialog(FinestraRegistrazione.this, "Compila tutti i campi!", "Errore", JOptionPane.WARNING_MESSAGE);
					return;
				}

				// AVVOLGIAMO TUTTO NEL TRY-CATCH PER ASCOLTARE LE TUE ECCEZIONI
                try {
                    // 2. Creiamo il CLIENTE
                    Cliente nuovoUtente = new Cliente();
                    
                    String nomeCompleto = txtNome.getText().trim();
                    if (nomeCompleto.contains(" ")) {
                        int spazio = nomeCompleto.indexOf(" ");
                        nuovoUtente.setNome(nomeCompleto.substring(0, spazio));
                        nuovoUtente.setCognome(nomeCompleto.substring(spazio + 1));
                    } else {
                        nuovoUtente.setNome(nomeCompleto);
                        nuovoUtente.setCognome("N/A"); 
                    }

                    // QUI SCATTANO LE TUE ECCEZIONI SE I DATI SONO SBAGLIATI!
                    nuovoUtente.setEmail(txtEmail.getText());
                    nuovoUtente.setPassword(new String(txtPassword.getPassword()));
                    
                    // 3. Lo salviamo nel DB tramite la Facade
                    BarbershopFacade.getInstance().registraUtente(nuovoUtente);
                    
                    // Se arriva qui, vuol dire che nessuna eccezione è scattata e il DB ha salvato!
                    JOptionPane.showMessageDialog(FinestraRegistrazione.this, "Registrazione completata! Ora puoi fare il login.");
                    
                    // Chiudiamo questa finestra e riapriamo il login
                    dispose();
                    new FinestraLogin().setVisible(true);

                } catch (RuntimeException ex) {
                    // CATTURA LE TUE ECCEZIONI (InvalidFormatException e EmptyFieldException)
                    // Il metodo ex.getMessage() prende ESATTAMENTE la frase che hai scritto tu in Utente.java!
                    JOptionPane.showMessageDialog(FinestraRegistrazione.this, ex.getMessage(), "Errore Dati", JOptionPane.ERROR_MESSAGE);
                    
                } catch (Exception ex) {
                    // Cattura eventuali errori di connessione al Database
                    JOptionPane.showMessageDialog(FinestraRegistrazione.this, "Errore di sistema durante il salvataggio.", "Errore Server", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

		// Cosa succede se clicco "Torna al Login"?
		btnAnnulla.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose(); // Chiude la registrazione
				new FinestraLogin().setVisible(true); // Riapre il login
			}
			
		});
	}
}
