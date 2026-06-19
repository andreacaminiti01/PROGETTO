package unipv.barbershop.dao.daoUtente;

import unipv.barbershop.database.DBConnection;
import unipv.barbershop.model.user.Utente;
import unipv.barbershop.model.user.Cliente;
import unipv.barbershop.model.staff.Barbiere;
import unipv.barbershop.model.user.Amministratore;
import unipv.barbershop.model.user.exception.CredenzialiErrateException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UtenteDAO implements IUtenteDAO {

    // 1. VARIABILI DI ISTANZA (
    private String schema;

    // 2. COSTRUTTORE 
    public UtenteDAO() {
        super();
        this.schema = "barbershop"; 
    }

    @Override
    public void inserisciUtente(Utente utente) {
        PreparedStatement ps = null; // Il "corriere" nasce e muore nel metodo
        Connection connLocale = null; // 1. Variabile locale

        try {
            // 2. Apriamo la connessione specifica per questa INSERT
            connLocale = DBConnection.getInstance().startConnection(schema);
            
            if (connLocale == null) {
                System.err.println("Errore: Impossibile connettersi al DB per l'inserimento.");
                return;
            }

            String query = "INSERT INTO utenti (nome, cognome, email, password, tipo, ruolo) VALUES (?, ?, ?, ?, ?, ?)";
            ps = connLocale.prepareStatement(query); // 3. Usiamo connLocale
            
            ps.setString(1, utente.getNome());
            ps.setString(2, utente.getCognome());
            ps.setString(3, utente.getEmail());
            ps.setString(4, utente.getPassword());

            // POLIMORFISMO: Chiedo all'oggetto di dirmi chi è (CLIENTE o AMMINISTRATORE)
            ps.setString(5, utente.getTipoDatabase());
            ps.setString(6, utente.getDettaglioRuolo());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 4. Pulizia totale
            try { if (ps != null) ps.close(); } catch (SQLException e) {}
            DBConnection.getInstance().closeConnection(connLocale);
        }
    }

    @Override
    public Utente login(String email, String password) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Utente utenteTrovato = null;
     // 1. DICHIARIAMO LA CONNESSIONE QUI DENTRO (Variabile locale)
        Connection connLocale = null; 

        try {
            // 2. APRIAMO LA CONNESSIONE SOLO PER QUESTA OPERAZIONE
            connLocale = DBConnection.getInstance().startConnection(schema);
            
            // Controlliamo che la connessione sia partita davvero
            if (connLocale == null) {
                System.err.println("Errore: Impossibile stabilire una connessione al DB!");
                return null;
            }

            String query = "SELECT * FROM utenti WHERE email = ? AND password = ?";
            
            // 3. USIAMO LA CONNESSIONE LOCALE
            ps = connLocale.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, password);

            rs = ps.executeQuery();

            if (rs.next()) {
                String tipo = rs.getString("tipo");

                if ("CLIENTE".equals(tipo)) {
                    Cliente c = new Cliente();
                    c.setId(rs.getInt("id"));
                    c.setNome(rs.getString("nome"));
                    c.setCognome(rs.getString("cognome"));
                    c.setEmail(rs.getString("email"));
                    c.setPassword(rs.getString("password"));
                    utenteTrovato = c;
                } 
                else if ("AMMINISTRATORE".equals(tipo)) {
                    Amministratore a = new Amministratore();
                    a.setId(rs.getInt("id"));
                    a.setNome(rs.getString("nome"));
                    a.setCognome(rs.getString("cognome"));
                    a.setEmail(rs.getString("email"));
                    a.setPassword(rs.getString("password"));
                    a.setRuolo(rs.getString("ruolo"));
                    utenteTrovato = a;
                }
            } else {
                throw new CredenzialiErrateException();
            }

        } catch (CredenzialiErrateException ce) {
            throw ce; 
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 4. CHIUDIAMO TUTTO IN MODO PULITO
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (ps != null) ps.close(); } catch (SQLException e) {}
            
            // Chiudiamo la connessione locale: così la prossima volta sarà di nuovo nuova
            DBConnection.getInstance().closeConnection(connLocale);
        }
        
        return utenteTrovato;
    }
    @Override
    public List<Utente> leggiTuttiGliUtenti() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        // Uso l'interfaccia List ma creo concretamente un ArrayList
        List<Utente> listaUtenti = new ArrayList<>();
        Connection connLocale = null; // 1. Variabile locale

        try {
            // 2. Apriamo la connessione
            connLocale = DBConnection.getInstance().startConnection(schema);
            
            if (connLocale == null) return listaUtenti;

            String query = "SELECT * FROM utenti";
            ps = connLocale.prepareStatement(query); // 3. Usiamo connLocale
            rs = ps.executeQuery();

            while (rs.next()) {
                String tipo = rs.getString("tipo");
                
                if ("CLIENTE".equals(tipo)) {
                    Cliente c = new Cliente();
                    c.setId(rs.getInt("id"));
                    c.setNome(rs.getString("nome"));
                    c.setCognome(rs.getString("cognome"));
                    c.setEmail(rs.getString("email"));
                    c.setPassword(rs.getString("password"));
                    listaUtenti.add(c);
                } 
                else if ("AMMINISTRATORE".equals(tipo)) {
                    Amministratore a = new Amministratore();
                    a.setId(rs.getInt("id"));
                    a.setNome(rs.getString("nome"));
                    a.setCognome(rs.getString("cognome"));
                    a.setEmail(rs.getString("email"));
                    a.setPassword(rs.getString("password"));
                    a.setRuolo(rs.getString("ruolo"));
                    listaUtenti.add(a);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 4. Chiusura di tutto il set
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (ps != null) ps.close(); } catch (SQLException e) {}
            DBConnection.getInstance().closeConnection(connLocale);
        }
        
        return listaUtenti;
    }
    public List<Barbiere> getTuttiIBarbieri() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Barbiere> listaBarbieri = new ArrayList<>();
        Connection connLocale = null;

        try {
            connLocale = DBConnection.getInstance().startConnection(schema);
            if (connLocale == null) return listaBarbieri;

            // Prende solo gli utenti che sono amministratori/barbieri
            String query = "SELECT * FROM utenti WHERE tipo = 'AMMINISTRATORE'"; 
            ps = connLocale.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                Barbiere b = new Barbiere();
                b.setId(rs.getInt("id"));
                b.setNome(rs.getString("nome"));
                b.setCognome(rs.getString("cognome"));
                
                listaBarbieri.add(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (ps != null) ps.close(); } catch (SQLException e) {}
            DBConnection.getInstance().closeConnection(connLocale);
        }
        
        return listaBarbieri;
    }
}
	



