package unipv.barbershop.controller;
import unipv.barbershop.facade.BarbershopFacade;
import unipv.barbershop.model.user.Cliente;
import unipv.barbershop.model.user.exception.EmptyFieldException;
import unipv.barbershop.model.user.exception.InvalidFormatException;
public class RegistrazioneController {
	private BarbershopFacade facade;

    public RegistrazioneController() {
        // Otteniamo sempre la nostra unica istanza della Facade
        this.facade = BarbershopFacade.getInstance();
    }

    /**
     * Metodo chiamato dall'interfaccia grafica quando l'utente clicca "Registrati"
     * Nota: Chi si registra da fuori è sempre un Cliente. Gli Admin vengono inseriti a mano nel DB.
     */
    public boolean registraNuovoCliente(String nome, String cognome, String email, String password, String telefono) {
        
        try {
            // 1. CREAZIONE DELL'OGGETTO (Polimorfismo e Incapsulamento)
           
            Cliente nuovoCliente = new Cliente(nome, cognome, email, password, telefono);

            // 2. SALVATAGGIO TRAMITE FACADE
           
            facade.registraUtente(nuovoCliente);

            System.out.println("Registrazione completata con successo per l'utente: " + email);
            
            // Opzionale: Autologin subito dopo la registrazione
            facade.login(email, password); 

            return true;

        } catch (EmptyFieldException | InvalidFormatException e) {
            // 3. GESTIONE ERRORI DI VALIDAZIONE 
            System.out.println("Errore di validazione: " + e.getMessage());
            // Il controller restituisce false, così Fabio sa che deve mostrare un popup di errore all'utente
            return false;
            
        } catch (Exception e) {
            // 4. GESTIONE ERRORI DI SISTEMA (es. Email già presente nel Database)
            System.out.println("Errore di registrazione: L'email potrebbe essere già in uso o il DB è offline.");
            e.printStackTrace();
            return false;
        }
    }
}
