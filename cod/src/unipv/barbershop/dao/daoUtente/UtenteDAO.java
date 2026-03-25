package unipv.barbershop.dao.daoUtente;

import unipv.barbershop.database.DBConnection;
import unipv.barbershop.model.user.Utente;
import unipv.barbershop.model.user.Cliente;
import unipv.barbershop.model.user.Amministratore;
import unipv.barbershop.model.user.Exception.CredenzialiErrateException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UtenteDAO implements IUtenteDAO {

    // 1. VARIABILI DI ISTANZA (
    private String schema;
    private Connection conn;

    // 2. COSTRUTTORE 
    public UtenteDAO() {
        super();
        this.schema = "barbershop"; // Inserisci qui il nome esatto del tuo database su Workbench
    }

    @Override
    public void inserisciUtente(Utente utente) {
        PreparedStatement ps = null; // Il "corriere" nasce e muore nel metodo

        try {
            // Uso this.conn e this.schema definiti all'inizio!
            this.conn = DBConnection.startConnection(this.conn, this.schema);
            // Prepariamo la query con i punti di domanda (?)
			// I punti di domanda sono fondamentali: evitano che gli hacker blocchino il database!
            String query = "INSERT INTO utenti (nome, cognome, email, password, tipo, ruolo) VALUES (?, ?, ?, ?, ?, ?)";
            ps = this.conn.prepareStatement(query);
            
            ps.setString(1, utente.getNome());
            ps.setString(2, utente.getCognome());
            ps.setString(3, utente.getEmail());
            ps.setString(4, utente.getPassword());

            // POLIMORFISMO: Chiedo all'oggetto di dirmi chi è
            ps.setString(5, utente.getTipoDatabase());
            ps.setString(6, utente.getDettaglioRuolo());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {//chiudendo nel finally ci assicuriamo che il ResultSet e la Connection vengano chiusi sempre e comunque, anche in caso di problemi nel try-catch
            try { if (ps != null) ps.close(); } catch (SQLException e) {}
            // Chiudo la connessione di istanza
            DBConnection.closeConnection(this.conn);
        }
    }

    @Override
    public Utente login(String email, String password) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Utente utenteTrovato = null;

        try {
            this.conn = DBConnection.startConnection(this.conn, this.schema);
            String query = "SELECT * FROM utenti WHERE email = ? AND password = ?";
            ps = this.conn.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, password);

            rs = ps.executeQuery();

            if (rs.next()) {
                // Leggo la colonna tipo dal DB per capire chi sto loggando
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
                    a.setRuolo(rs.getString("ruolo")); // L'admin ha anche il ruolo
                    utenteTrovato = a;
                }
            } else {
                // Se la tabella è vuota, l'utente ha sbagliato i dati!
                throw new CredenzialiErrateException();
            }

        } catch (CredenzialiErrateException ce) {
            throw ce; // Rilancio l'eccezione verso l'interfaccia grafica
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (ps != null) ps.close(); } catch (SQLException e) {}
            DBConnection.closeConnection(this.conn);
        }
        
        return utenteTrovato;
    }

    @Override
    public List<Utente> leggiTuttiGliUtenti() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        // Uso l'interfaccia List ma creo concretamente un ArrayList
        List<Utente> listaUtenti = new ArrayList<>();

        try {
            this.conn = DBConnection.startConnection(this.conn, this.schema);
            String query = "SELECT * FROM utenti";
            ps = this.conn.prepareStatement(query);
            rs = ps.executeQuery();

            // Ciclo WHILE per scorrere tutta la tabella del database
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
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (ps != null) ps.close(); } catch (SQLException e) {}
            DBConnection.closeConnection(this.conn);
        }
        
        return listaUtenti;
    }
}

/*	@Override
	public boolean registraUtente(Utente utente) {
		// 1. Apriamo la connessione usando la tua classe DBConnection
		conn = DBConnection.startConnection(conn, schema);
		PreparedStatement st1 = null;
		boolean esito = false;

		try {
			// 2. Prepariamo la query con i punti di domanda (?)
			// I punti di domanda sono fondamentali: evitano che gli hacker blocchino il database!
			String query = "INSERT INTO utenti (nome, cognome, email, password, ruolo, telefono) VALUES (?, ?, ?, ?, ?, ?)";
			st1 = conn.prepareStatement(query);

			// 3. Riempiamo i punti di domanda con i dati dell'oggetto Utente
			st1.setString(1, utente.getNome());
			st1.setString(2, utente.getCognome());
			st1.setString(3, utente.getEmail());
			st1.setString(4, utente.getPassword());
			st1.setString(5, utente.getRuolo());
			// Capiamo se l'utente che stiamo salvando è un Cliente o un Amministratore
			// Se è un Cliente, ci darà il numero. Se è Amministratore, ci darà "null".
			if (utente.getTelefono() != null) {
			    st1.setString(6, utente.getTelefono());
			} else {
			    st1.setNull(6, java.sql.Types.VARCHAR);
			}

			// 4. Eseguiamo il comando e confermiamo che è andato a buon fine
			st1.executeUpdate();
			esito = true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		// 5. Chiudiamo la connessione in modo pulito
		DBConnection.closeConnection(conn);
		return esito;
	}*/
	
	



