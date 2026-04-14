package unipv.barbershop.view.admin;

import javax.swing.*;
import java.awt.*;
import unipv.barbershop.facade.BarbershopFacade;
import unipv.barbershop.model.user.Utente;

public class DashboardAdmin extends JFrame {
	// Bottoni per le funzioni amministrative
    private JButton btnGestioneMagazzino;
    private JButton btnVisualizzaPrenotazioni;
    private JButton btnLogout;

    public DashboardAdmin() {
        // 1. Configurazione Finestra
        setTitle("Barbershop - Pannello Amministratore");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 2. Recupero dati dall'unica Facade (Singleton)
        Utente admin = BarbershopFacade.getInstance().getLoggedUser();
        String nomeAdmin = (admin != null) ? admin.getNome() : "Admin";

        // 3. Intestazione
        JPanel panelHeader = new JPanel();
        JLabel lblTitolo = new JLabel("Pannello di Controllo: " + nomeAdmin);
        lblTitolo.setFont(new Font("Arial", Font.BOLD, 22));
        panelHeader.add(lblTitolo);

        // 4. Area Centrale con i comandi
        JPanel panelMenu = new JPanel(new GridLayout(3, 1, 15, 15));
        panelMenu.setBorder(BorderFactory.createEmptyBorder(40, 120, 40, 120));

        btnGestioneMagazzino = new JButton("📦 Gestione Magazzino");
        btnVisualizzaPrenotazioni = new JButton("📅 Visualizza Prenotazioni");
        btnLogout = new JButton("🚪 Esci dal Sistema");

        // Stile veloce per distinguere i bottoni admin
        btnGestioneMagazzino.setFocusPainted(false);
        btnVisualizzaPrenotazioni.setFocusPainted(false);
        btnLogout.setFocusPainted(false);

        panelMenu.add(btnGestioneMagazzino);
        panelMenu.add(btnVisualizzaPrenotazioni);
        panelMenu.add(btnLogout);

        // 5. Composizione finale
        add(panelHeader, BorderLayout.NORTH);
        add(panelMenu, BorderLayout.CENTER);
        
        // Aggiungiamo una piccola nota in basso per professionalità
        JLabel lblStatus = new JLabel(" Accesso autorizzato come: AMMINISTRATORE", SwingConstants.LEFT);
        lblStatus.setFont(new Font("Arial", Font.ITALIC, 11));
        add(lblStatus, BorderLayout.SOUTH);
    }

    // --- GETTER PER IL CONTROLLER ---
    // Il MagazzinoController userà questi per attivare le funzioni
    public JButton getBtnGestioneMagazzino() { 
    	return btnGestioneMagazzino; }
    public JButton getBtnVisualizzaPrenotazioni() { 
    	return btnVisualizzaPrenotazioni; }
    public JButton getBtnLogout() { 
    	return btnLogout; }
	

}
