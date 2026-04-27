package unipv.barbershop.main;
import unipv.barbershop.view.account.FinestraLogin;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import java.awt.Font;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
public class Main {

	public static void main(String[] args) {
		// 1. IMPOSTAZIONI AVANZATE "STILE WEB"
        try {
            // Arrotonda i bottoni come nei siti web moderni (es. stile Bootstrap)
            UIManager.put("Button.arc", 15); 
            // Arrotonda le barre di testo
            UIManager.put("TextComponent.arc", 15); 
            // Rimuove il noioso bordino azzurro quando clicchi su un componente
            UIManager.put("Component.focusWidth", 1); 
            // Cambia il font di base mettendone uno più grande e pulito (es. 14px)
            UIManager.put("defaultFont", new Font("Segoe UI", Font.PLAIN, 14));
            
            // Applica il tema chiaro di FlatLaf
            FlatLightLaf.setup();
            
        } catch (Exception e) {
            System.err.println("Errore nel caricamento del tema grafico");
        }
		// 2. AVVIAMO L'APPLICAZIONE (Esattamente come fanno loro con invokeLater)
		SwingUtilities.invokeLater(() -> {
			FinestraLogin login = new FinestraLogin();
			login.setVisible(true);
		});
	}

}

