package unipv.barbershop.main;
import unipv.barbershop.view.account.FinestraLogin;
import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.SwingUtilities;
public class Main {

	public static void main(String[] args) {
		// 1. ATTIVIAMO IL TEMA MODERNO
		try {
			FlatDarkLaf.setup();
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

