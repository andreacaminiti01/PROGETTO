package unipv.barbershop.view.cliente;

import javax.swing.*;
import java.awt.*;
import unipv.barbershop.facade.BarbershopFacade;
import unipv.barbershop.model.user.Utente;

public class DashboardCliente extends JFrame {

    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// I bottoni che il Controller dovrà "ascoltare"
    private JButton btnPrenota;
    private JButton btnFeedback;
    private JButton btnLogout;

    public DashboardCliente() {
        // 1. Setup Base della Finestra
        setTitle("Barbershop - Area Cliente");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Chiude l'app se si preme la X
        setLocationRelativeTo(null); // Centra la finestra al centro dello schermo
        setLayout(new BorderLayout());

        // 2. STRATEGIA FACADE: Chiediamo chi è entrato!
        Utente utenteLoggato = BarbershopFacade.getInstance().getLoggedUser();
        String nomeCliente = (utenteLoggato != null) ? utenteLoggato.getNome() : "Cliente";

        // 3. Pannello Superiore (Il Titolo/Benvenuto)
        JPanel panelTop = new JPanel();
        JLabel lblBenvenuto = new JLabel("Benvenuto, " + nomeCliente + "!");
        lblBenvenuto.setFont(new Font("Arial", Font.BOLD, 24));
        panelTop.add(lblBenvenuto);

        // 4. Pannello Centrale (I Bottoni Operativi)
        // GridLayout(3, 1, 10, 10) significa: 3 righe, 1 colonna, con 10px di spazio tra loro
        JPanel panelCenter = new JPanel(new GridLayout(3, 1, 10, 10));
        // Mettiamo un po' di margine (padding) per non avere bottoni giganti attaccati ai bordi
        panelCenter.setBorder(BorderFactory.createEmptyBorder(50, 150, 50, 150));

        btnPrenota = new JButton("Nuova Prenotazione");
        btnFeedback = new JButton("I Miei Appuntamenti / Feedback");
        btnLogout = new JButton("Logout");

        panelCenter.add(btnPrenota);
        panelCenter.add(btnFeedback);
        panelCenter.add(btnLogout);

        // 5. Assembliamo i pezzi nella finestra principale
        add(panelTop, BorderLayout.NORTH);
        add(panelCenter, BorderLayout.CENTER);
    }

    // GETTER 
    // Questi sono FONDAMENTALI: permettono al Controller di "agganciare" i bottoni
    // senza doverli rendere public e rovinare l'incapsulamento!
    public JButton getBtnPrenota() { 
    	return btnPrenota; }
    public JButton getBtnFeedback() { 
    	return btnFeedback; }
    public JButton getBtnLogout() { 
    	return btnLogout; }
}