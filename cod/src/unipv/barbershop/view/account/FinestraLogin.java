package unipv.barbershop.view.account;
import unipv.barbershop.facade.BarbershopFacade;
import unipv.barbershop.model.user.Utente;
import unipv.barbershop.controller.LoginController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class FinestraLogin extends JFrame {
	private JTextField txtEmail;
	private JPasswordField txtPassword;
	private JButton btnAccedi;
	private JButton btnRegistrati;

	private LoginController loginController;

	public FinestraLogin() {
		this.loginController = new LoginController();

		// --- 1. IMPOSTAZIONI FINESTRA ---
		setTitle("Barbershop - Autenticazione");
		setSize(400, 350);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null); // Centra la finestra nello schermo
		setResizable(false); // Impedisce di ridimensionarla

		// --- 2. DESIGN PRINCIPALE (Uso dei margini per far "respirare" l'app) ---
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40)); // Padding: su, destra, giù, sinistra

		// Titolo
		JLabel lblTitolo = new JLabel("B A R B E R S H O P");
		lblTitolo.setFont(new Font("Segoe UI", Font.BOLD, 24));
		lblTitolo.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrato

		// Spazio vuoto tra titolo e campi
		mainPanel.add(lblTitolo);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

		// --- 3. CAMPI DI TESTO ---
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setAlignmentX(Component.CENTER_ALIGNMENT);
		txtEmail = new JTextField(20);
		txtEmail.setMaximumSize(new Dimension(300, 35));

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
		txtPassword = new JPasswordField(20);
		txtPassword.setMaximumSize(new Dimension(300, 35));

		mainPanel.add(lblEmail);
		mainPanel.add(txtEmail);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
		mainPanel.add(lblPassword);
		mainPanel.add(txtPassword);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

		// --- 4. BOTTONI (Con stile FlatLaf arrotondato) ---
		btnAccedi = new JButton("Accedi");
		btnAccedi.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnAccedi.putClientProperty("JButton.buttonType", "roundRect"); // Magia FlatLaf per arrotondare!

		btnRegistrati = new JButton("Crea un account Cliente");
		btnRegistrati.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnRegistrati.putClientProperty("JButton.buttonType", "roundRect");

		mainPanel.add(btnAccedi);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		mainPanel.add(btnRegistrati);

		add(mainPanel);

		// --- 5. AZIONI DEI BOTTONI ---

		btnAccedi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String email = txtEmail.getText();
				String password = new String(txtPassword.getPassword());

				// Chiamata al TUO Backend
				if (loginController.gestisciLogin(email, password)) {
					Utente u = BarbershopFacade.getInstance().getLoggedUser();
					JOptionPane.showMessageDialog(FinestraLogin.this, "Accesso eseguito come: " + u.getRuolo());

					dispose(); // Chiude il login

					// Smistamento
					if (u.getRuolo().equals("AMMINISTRATORE")) {
						// TODO: Aprire schermata Magazzino
					} else {
						// TODO: Aprire schermata Cliente
					}
				} else {
					JOptionPane.showMessageDialog(FinestraLogin.this, "Credenziali errate!", "Errore", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		btnRegistrati.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(FinestraLogin.this, "Qui si aprirà la finestra di registrazione!");
				 dispose();
				 new FinestraRegistrazione().setVisible(true);
			}
		});
	}
}
