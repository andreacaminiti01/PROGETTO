package unipv.barbershop.controller;

import unipv.barbershop.view.admin.DashboardAdmin;
import unipv.barbershop.view.admin.FinestraMagazzino;
import unipv.barbershop.view.admin.FinestraVisualizzaPrenotazioni;
import unipv.barbershop.view.account.FinestraLogin;
import unipv.barbershop.facade.BarbershopFacade;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class DashboardAdminController {
	
	private DashboardAdmin vistaAdmin;
	
	public DashboardAdminController (DashboardAdmin vistaAdmin ) {
	    this.vistaAdmin = vistaAdmin;
	    inizializzaEventi();
		
	}
	
	private void inizializzaEventi () {
		
		//Tasto gestione magazzino
		vistaAdmin.getBtnGestioneMagazzino().addActionListener(new  ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Quando l'admin clicca, apriamo la finestra del magazzino
                FinestraMagazzino vistaMagazzino = new FinestraMagazzino();
                
                // (Qui poi aggiungeremo il MagazzinoController per riempire la tabella!)
                
                vistaMagazzino.setVisible(true);
			}
		});
		
		//TASTO VISUALIZZA PRENOTAZIONI
		vistaAdmin.getBtnVisualizzaPrenotazioni().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	FinestraVisualizzaPrenotazioni vistaPrenotazioni = new FinestraVisualizzaPrenotazioni();
                
                // 2. Recuperiamo la lista di prenotazioni dal database tramite la Facade
                java.util.List<String[]> datiPrenotazioni = BarbershopFacade.getInstance().getElencoPrenotazioniAdmin();
                
                // 3. Prendiamo il "modello" della tabella (il cervello dei dati)
                javax.swing.table.DefaultTableModel model = vistaPrenotazioni.getTableModel();
                
                // 4. Puliamo la tabella (per sicurezza) e aggiungiamo le righe una ad una
                model.setRowCount(0); 
                for (String[] riga : datiPrenotazioni) {
                    model.addRow(riga);
                }
                
                // 5. Rendiamo la finestra visibile
                vistaPrenotazioni.setVisible(true);
            
                
            }
        });
		
		// 3. TASTO LOGOUT (Il più importante per la sicurezza)
        vistaAdmin.getBtnLogout().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int conferma = JOptionPane.showConfirmDialog(vistaAdmin, 
                    "Sei sicuro di voler uscire?", "Conferma Logout", JOptionPane.YES_NO_OPTION);
                
                if (conferma == JOptionPane.YES_OPTION) {
                    // Puliamo la sessione chiamando la Facade
                    BarbershopFacade.getInstance().logout();
                    
                    // Chiudiamo la dashboard admin
                    vistaAdmin.dispose();
                    
                    // Riportiamo l'utente alla schermata di Login
                    FinestraLogin login = new FinestraLogin();
                    // Assicurati che LoginController sia agganciato alla finestra di login nel tuo codice Main
                    login.setVisible(true); 
                }
            }
        });
    }
		
		
		
	}
	


