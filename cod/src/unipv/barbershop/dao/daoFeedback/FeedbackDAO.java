package unipv.barbershop.dao.daoFeedback;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import unipv.barbershop.model.feedback.Feedback;
import unipv.barbershop.database.DBConnection;
import unipv.barbershop.model.user.Cliente;

public class FeedbackDAO implements IFeedbackDAO {
	private String schema = "barbershop";


	@Override
	public boolean salvaFeedback(Feedback f) {
		// Usiamo la connessione locale per sicurezza e multithreading
		Connection connLocale = null;
		PreparedStatement ps = null;
		boolean esito = false;

		try {
			connLocale = DBConnection.getInstance().startConnection(schema);

			String query = "INSERT INTO feedback (id_cliente, id_prenotazione, voto, commento) VALUES (?, ?, ?, ?)";
			ps = connLocale.prepareStatement(query);

			ps.setInt(1, f.getCliente().getId());
			ps.setInt(2, f.getPrenotazioneRiferimento().getId());
			ps.setInt(3, f.getVoto());
			ps.setString(4, f.getCommento());

			ps.executeUpdate();
			esito = true;

		} catch (java.sql.SQLIntegrityConstraintViolationException e) {
			// CATTURIAMO IL DUPLICATO: Niente scritte rosse, stampiamo un messaggio pulito in console
			System.out.println("Avviso: Il cliente ha già lasciato un feedback per questa prenotazione.");
			esito = false;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try { if (ps != null) ps.close(); } catch (SQLException e) {}
			DBConnection.getInstance().closeConnection(connLocale);
		}
		return esito;
	}

	@Override
	public List<Feedback> recuperaTuttiIFeedback() {
		// Usiamo la connessione locale per evitare conflitti
		Connection connLocale = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Feedback> listaFeedback = new ArrayList<>();

		try {
			connLocale = DBConnection.getInstance().startConnection(schema);

			// Facciamo una JOIN per recuperare i dati del cliente e della prenotazione insieme al feedback
			String query = "SELECT f.*, u.nome, u.cognome, p.data_ora " +
					"FROM feedback f " +
					"JOIN utenti u ON f.id_cliente = u.id " +
					"JOIN prenotazioni p ON f.id_prenotazione = p.id";

			ps = connLocale.prepareStatement(query);
			rs = ps.executeQuery();

			while (rs.next()) {
				// 1. Ricreiamo l'oggetto Cliente (l'autore)
				Cliente autore = new Cliente();
				autore.setId(rs.getInt("id_cliente"));
				autore.setNome(rs.getString("nome"));
				autore.setCognome(rs.getString("cognome"));

				// 2. Ricreiamo l'oggetto Prenotazione (il riferimento)
				unipv.barbershop.model.booking.Prenotazione preno = new unipv.barbershop.model.booking.Prenotazione();
				preno.setId(rs.getInt("id_prenotazione"));
				preno.setDataOra(rs.getTimestamp("data_ora").toLocalDateTime());

				// 3. Creiamo l'oggetto Feedback completo
				Feedback f = new Feedback();
				f.setId(rs.getInt("id"));
				f.setCliente(autore);
				f.setPrenotazioneRiferimento(preno);
				f.setVoto(rs.getInt("voto"));
				f.setCommento(rs.getString("commento"));

				listaFeedback.add(f);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Pulizia totale degli strumenti
			try { if (rs != null) rs.close(); } catch (SQLException e) {}
			try { if (ps != null) ps.close(); } catch (SQLException e) {}
			DBConnection.getInstance().closeConnection(connLocale);
		}

		return listaFeedback;
	}

}
