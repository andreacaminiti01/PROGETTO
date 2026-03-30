package unipv.barbershop.dao.daoPrenotazione;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;

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
		// 1. PRIMA DI TUTTO: Controlliamo se c'è posto!
		// Se il barbiere NON è disponibile, scatta subito la TUA eccezione e blocca tutto!
		if (!isBarbiereDisponibile(p.getBarbiere().getId(), p.getDataOra())) {
			throw new PostiEsauritiException();
		}

		conn = DBConnection.getInstance().startConnection(schema);
        boolean esito = false;

		try {
			// 2. INIZIO TRANSAZIONE: Spegniamo il salvataggio automatico
			conn.setAutoCommit(false); 

			// 3. Salviamo i dati principali della prenotazione
			String queryPrenotazione = "INSERT INTO prenotazioni (id_cliente, id_barbiere, data_ora) VALUES (?, ?, ?)";
			PreparedStatement st1 = conn.prepareStatement(queryPrenotazione, Statement.RETURN_GENERATED_KEYS);
			st1.setInt(1, p.getCliente().getId());
			st1.setInt(2, p.getBarbiere().getId());
			st1.setObject(3, p.getDataOra());

			st1.executeUpdate();

			// 4. Recuperiamo l'ID appena generato da MySQL
			ResultSet rsKeys = st1.getGeneratedKeys();
			int idPrenotazione = -1;
			if (rsKeys.next()) {
				idPrenotazione = rsKeys.getInt(1);
			}

			// 5. Salviamo TUTTI i servizi scelti nella tabella ponte
			String queryServizi = "INSERT INTO prenotazioni_servizi (id_prenotazione, id_servizio) VALUES (?, ?)";
			PreparedStatement st2 = conn.prepareStatement(queryServizi);

			// Usiamo il metodo getServiziScelti() !
			for (Servizio s : p.getServiziScelti()) {
				st2.setInt(1, idPrenotazione);
				st2.setInt(2, s.getId());
				st2.executeUpdate();
			}

			// 6. TUTTO OK: Confermiamo il salvataggio nel database
			conn.commit(); 
			esito = true;

		} catch (Exception e) {
			e.printStackTrace();
			try {
				// SE C'È UN ERRORE: Annulliamo tutto e non salviamo niente a metà
				if (conn != null) conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} finally {
			try {
				if (conn != null) conn.setAutoCommit(true); // Riaccendiamo il salvataggio automatico
			} catch (Exception e) {}
			DBConnection.getInstance().closeConnection(conn);
		}

		return esito;
	}


}
