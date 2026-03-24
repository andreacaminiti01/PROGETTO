package unipv.barbershop.dao.daoUtente;
import java.sql.PreparedStatement;
import java.sql.Connection;
import unipv.barbershop.model.user.Utente;
import unipv.barbershop.database.DBConnection;

public class UtenteDAO implements IUtenteDAO {


	private String schema;
	private Connection conn;

	public UtenteDAO() {
		super();
		this.schema = "barbershop"; // Il nome esatto del tuo database
	}

	@Override
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
	}


}
