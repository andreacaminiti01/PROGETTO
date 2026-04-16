package unipv.barbershop.dao.daoPrenotazione;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import unipv.barbershop.database.DBConnection;
import unipv.barbershop.model.booking.Prenotazione;
import unipv.barbershop.model.booking.exception.PostiEsauritiException;
import unipv.barbershop.model.booking.Servizio;


public class PrenotazioneDAO implements IPrenotazioneDAO {
	private String schema = "barbershop";
	private Connection conn;

	@Override
	public boolean isBarbiereDisponibile(int idBarbiere, LocalDateTime dataOra) {
		conn = DBConnection.getInstance().startConnection(schema);
		boolean disponibile = true;

		try {
			// Controlliamo se c'è già una riga con quello stesso barbiere a quella stessa ora
			String query = "SELECT id FROM prenotazioni WHERE id_barbiere = ? AND data_ora = ?";
			PreparedStatement st = conn.prepareStatement(query);
			st.setInt(1, idBarbiere);
			st.setObject(2, dataOra); 

			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				disponibile = false; // Trovato! Il barbiere è occupato
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.getInstance().closeConnection(conn);
		}
		return disponibile;
	}

	@Override
	public boolean salvaPrenotazione(Prenotazione p) throws PostiEsauritiException {
	    
	    // 1. PRIMA DI TUTTO: Controlliamo se il barbiere è libero per quell'ora
	    // Se non è disponibile, lanciamo l'eccezione personalizzata e il metodo si ferma qui
	    if (!isBarbiereDisponibile(p.getBarbiere().getId(), p.getDataOra())) {
	        throw new PostiEsauritiException();
	    }

	    // DICHIARIAMO GLI "ATTREZZI" FUORI DAL TRY: così il blocco finally potrà pulirli alla fine!
	    Connection conn = null;
	    PreparedStatement st1 = null; // Per la tabella prenotazioni
	    PreparedStatement st2 = null; // Per la tabella ponte prenotazioni_servizi
	    ResultSet rsKeys = null;      // Per recuperare l'ID appena creato
	    boolean esito = false;

	    try {
	        // 2. APRIAMO LA CONNESSIONE: Usiamo l'istanza Singleton
	        conn = DBConnection.getInstance().startConnection(schema);
	        
	        // 3. INIZIO TRANSAZIONE: Diciamo a MySQL di non salvare nulla finché non diamo il comando finale (commit)
	        conn.setAutoCommit(false); 

	        // 4. SALVATAGGIO DATI BASE: Inseriamo cliente, barbiere e data/ora
	        // Chiediamo a MySQL di restituirci l'ID che genererà (RETURN_GENERATED_KEYS)
	        String queryPrenotazione = "INSERT INTO prenotazioni (id_cliente, id_barbiere, data_ora) VALUES (?, ?, ?)";
	        st1 = conn.prepareStatement(queryPrenotazione, Statement.RETURN_GENERATED_KEYS);
	        st1.setInt(1, p.getCliente().getId());
	        st1.setInt(2, p.getBarbiere().getId());
	        st1.setObject(3, p.getDataOra()); // Usiamo setObject per il LocalDateTime

	        st1.executeUpdate();

	        // 5. RECUPERO ID GENERATO: Prendiamo il numero identificativo appena creato da MySQL
	        rsKeys = st1.getGeneratedKeys();
	        int idPrenotazione = -1; // "Valore sentinella"
	        if (rsKeys.next()) {
	            idPrenotazione = rsKeys.getInt(1); // Ora idPrenotazione contiene l'ID vero (es. 15)
	        }

	        // 6. SALVATAGGIO SERVIZI (TABELLA PONTE): Colleghiamo la prenotazione ai servizi scelti
	        String queryServizi = "INSERT INTO prenotazioni_servizi (id_prenotazione, id_servizio) VALUES (?, ?)";
	        st2 = conn.prepareStatement(queryServizi);

	        // Cicliamo sulla lista di servizi scelti dal cliente
	        for (Servizio s : p.getServiziScelti()) {
	            st2.setInt(1, idPrenotazione);
	            st2.setInt(2, s.getId());
	            st2.executeUpdate(); // Ogni servizio diventa una riga nella tabella ponte
	        }

	        // 7. FINE TRANSAZIONE: Se siamo arrivati qui senza errori, salviamo tutto definitivamente!
	        conn.commit(); 
	        esito = true;

	    } catch (Exception e) {
	        // 8. GESTIONE ERRORI: Se qualcosa va storto, annulliamo tutto quello che avevamo iniziato (Rollback)
	        e.printStackTrace();
	        try {
	            if (conn != null) conn.rollback(); 
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    } finally {
	        // 9. PULIZIA TOTALE (IL FINALLY SALVA IL DB!): Chiudiamo tutto quello che abbiamo aperto
	        try {
	            if (conn != null) conn.setAutoCommit(true); // Riportiamo la connessione allo stato normale
	        } catch (Exception e) {}
	        
	        // Chiudiamo i corrieri (Statement) e i risultati (ResultSet)
	        try { if (rsKeys != null) rsKeys.close(); } catch (SQLException e) {}
	        try { if (st1 != null) st1.close(); } catch (SQLException e) {}
	        try { if (st2 != null) st2.close(); } catch (SQLException e) {}
	        
	        // Chiudiamo la connessione tramite il Singleton
	        DBConnection.getInstance().closeConnection(conn);
	    }

	    return esito;
	}
	@Override
	public List<Prenotazione> recuperaPrenotazioniPerCliente(int idCliente) {
	    List<Prenotazione> lista = new ArrayList<>();
	    // Usiamo una JOIN per prendere i dati della prenotazione E il nome del barbiere in un colpo solo
	    String query = "SELECT p.*, u.nome AS nomeB, u.cognome AS cognomeB " +
	                   "FROM prenotazioni p " +
	                   "JOIN utenti u ON p.id_barbiere = u.id " +
	                   "WHERE p.id_cliente = ? " + 
	                   "ORDER BY p.data_ora DESC";
	    
	    Connection connLocale = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    try {
	        connLocale = DBConnection.getInstance().startConnection(schema);
	        ps = connLocale.prepareStatement(query);
	        ps.setInt(1, idCliente);
	        rs = ps.executeQuery();

	        while (rs.next()) {
	            // Creiamo l'oggetto Prenotazione base
	            Prenotazione p = new Prenotazione();
	            p.setId(rs.getInt("id"));
	            
	            // Convertiamo il Timestamp del DB nel tuo LocalDateTime
	            p.setDataOra(rs.getTimestamp("data_ora").toLocalDateTime());
	            
	            // Creiamo l'oggetto Barbiere "dentro" la prenotazione
	            unipv.barbershop.model.staff.Barbiere b = new unipv.barbershop.model.staff.Barbiere();
	            b.setId(rs.getInt("id_barbiere"));
	            b.setNome(rs.getString("nomeB"));
	            b.setCognome(rs.getString("cognomeB"));
	            
	            p.setBarbiere(b);
	            
	            // Aggiungiamo alla lista che andrà alla JTable
	            lista.add(p);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        // Pulizia standard come nei tuoi altri metodi
	        try { if (rs != null) rs.close(); } catch (SQLException e) {}
	        try { if (ps != null) ps.close(); } catch (SQLException e) {}
	        DBConnection.getInstance().closeConnection(connLocale);
	    }
	    return lista;
	}
	
}