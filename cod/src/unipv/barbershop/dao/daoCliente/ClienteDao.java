package unipv.barbershop.dao.daoCliente;

import unipv.barbershop.database.DBConnection; 
import unipv.barbershop.model.user.Cliente;
import unipv.barbershop.model.user.exception.CredenzialiErrateException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteDao implements IClienteDao {
	
	private final String SCHEMA = "barbershop_db";
	
	@Override
    public void inserisciCliente(Cliente cliente) {
        Connection conn = null; //collegamento java e DB
        
        PreparedStatement ps = null; //colui che porta la query al DB
        
        try {
        	// Apro la connesione
        	conn = DBConnection.startConnection(conn, SCHEMA);
        	
        	//Preparo la qquery con i punti interrogativi(preparedStatement)
        	//per evitare SQL injection Un hacker potrebbe inserire come nome un pezzo di codice malevolo 
        	//(es. '); DROP TABLE clienti;--) e cancellare tutto il database!
        	//Usando i ? ava "igienizza" il testo. Anche se l'utente scrive codice maligno, 
        	//Java lo tratta come semplice testo innocuo.
        	String query = "INSERT INTO clienti (nome, cognome, email, password) VALUES (?, ?, ?, ?)";
        	ps = conn.prepareStatement(query);
        	
        	//  Inserisco i dati veri al posto dei punti interrogativi
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getCognome());
            ps.setString(3, cliente.getEmail());
            ps.setString(4, cliente.getPassword());
            
         // 4. Eseguo la query
            ps.executeUpdate(); //Si usa per le query che modificano il database 
            
             } catch (Exception e) {
            e.printStackTrace();
        } finally { //viene eseguito sempre, sia che la query vada a buon fine, sia che ci sia un errore. Lo usiamo per chiudere la connessione e svuotare la memoria.
            // 5. CHIUDO TUTTO 
            try { if (ps != null) ps.close(); } catch (SQLException e) {}
            DBConnection.closeConnection(conn); 
        }
    }
        
	@Override
    public Cliente login(String email, String password) {
		Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null; //È un "foglio Excel virtuale" in cui MySQL ci restituisce le risposte.
        Cliente clienteTrovato = null;
        
        try {
        	conn = DBConnection.startConnection(conn, SCHEMA);
            String query = "SELECT * FROM clienti WHERE email = ? AND password = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, password);
            
         // Per le SELECT si usa executeQuery che restituisce una tabella (ResultSet)
            rs = ps.executeQuery();
            
            if (rs.next()) {
                // Il database ha trovato una riga! Ricostruisco l'oggetto Cliente
                clienteTrovato = new Cliente();
                clienteTrovato.setId(rs.getInt("id"));
                clienteTrovato.setNome(rs.getString("nome"));
                clienteTrovato.setCognome(rs.getString("cognome"));
                clienteTrovato.setEmail(rs.getString("email"));
                clienteTrovato.setPassword(rs.getString("password"));
            } else {
                // IL DATABASE NON HA TROVATO NESSUNO! LANCIO ECCEZIONE!
                throw new CredenzialiErrateException();
            }
            
        } catch (CredenzialiErrateException ce) {
            // Catturo la mia eccezione e la "rilancio" al piano di sopra
            throw ce; 
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (ps != null) ps.close(); } catch (SQLException e) {}
            DBConnection.closeConnection(conn);
        }
            
            
        return clienteTrovato; 
        // Restituisce il cliente se il login ha successo
        
        
		
	}
	
}
        
	
	


