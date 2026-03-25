package unipv.barbershop.test;

import unipv.barbershop.dao.daoUtente.UtenteDAO;
import unipv.barbershop.model.user.Amministratore;
import unipv.barbershop.model.user.Cliente;
import unipv.barbershop.model.user.Utente;

import java.util.List;

public class MainTestDatabase {

    public static void main(String[] args) {
        
        System.out.println("--- AVVIO TEST DATABASE ---");
        
        // 1. Istanzio il mio DAO
        UtenteDAO dao = new UtenteDAO();
        
        // 2. Creo un Cliente finto in RAM (nome, cognome, email, password, telefono)
        Cliente c = new Cliente("Mario", "Rossi", "mario@email.it", "password123", "3331234567");
        
        // 3. Creo un Admin finto in RAM (nome, cognome, email, password, ruolo)
        Amministratore a = new Amministratore("Luigi", "Verdi", "admin@barber.it", "admin123", "Proprietario");

        System.out.println("Provo a salvare gli utenti nel database...");
        
        // 4. Li salvo fisicamente su MySQL!
        dao.inserisciUtente(c);
        dao.inserisciUtente(a);
        
        System.out.println("Salvataggio completato! Ora provo a rileggerli dal DB...\n");

        // 5. Provo a leggere tutti gli utenti per vedere se il Polimorfismo funziona
        List<Utente> listaDalDB = dao.leggiTuttiGliUtenti();
        
        for (Utente u : listaDalDB) {
            System.out.println("Trovato Utente: " + u.getNome() + " " + u.getCognome());
            System.out.println(" - Email: " + u.getEmail());
            System.out.println(" - Classe Java rilevata: " + u.getTipoDatabase());
            System.out.println(" - Ruolo specifico: " + u.getDettaglioRuolo());
            System.out.println("----------------------------------");
        }
        
        System.out.println("--- TEST COMPLETATO ---");
    }
}