package unipv.barbershop.dao.daoFeedback;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import unipv.barbershop.model.feedback.Feedback;
import unipv.barbershop.database.DBConnection;
import unipv.barbershop.model.user.Cliente;

public class FeedbackDAO implements IFeedbcakDAO {
	private String schema = "barbershop";
	private Connection conn;

	@Override
	public boolean salvaFeedback(Feedback f) {
		conn = DBConnection.getInstance().startConnection(schema);
		boolean esito = false;

		try {
			// Validazione in Java: Voto da 1 a 5 e Prenotazione presente
			if (f.getVoto() < 1 || f.getVoto() > 5) {
				System.out.println("Errore: Il voto deve essere compreso tra 1 e 5.");
				return false;
			}
			if (f.getPrenotazioneRiferimento() == null) {
				System.out.println("Errore: Il feedback deve essere collegato a una prenotazione.");
				return false;
			}

			// Aggiunto id_prenotazione alla query!
			String query = "INSERT INTO feedback (id_cliente, id_prenotazione, voto, commento) VALUES (?, ?, ?, ?)";
			PreparedStatement st = conn.prepareStatement(query);

			st.setInt(1, f.getCliente().getId());
			st.setInt(2, f.getPrenotazioneRiferimento().getId()); // Salviamo il collegamento!
			st.setInt(3, f.getVoto());
			st.setString(4, f.getCommento());

			st.executeUpdate();
			esito = true;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.getInstance().closeConnection(conn);
		}

		return esito;
	}

	@Override
	public List<Feedback> recuperaTuttiIFeedback() {
		conn = DBConnection.getInstance().startConnection(schema);
		List<Feedback> listaFeedback = new ArrayList<>();

		try {
			// Facciamo una JOIN per recuperare anche il nome del cliente che ha lasciato il commento!
			String query = "SELECT f.id, f.voto, f.commento, u.id AS id_cliente, u.nome, u.cognome " +
					"FROM feedback f JOIN utenti u ON f.id_cliente = u.id";

			PreparedStatement st = conn.prepareStatement(query);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				// Ricreiamo l'oggetto Cliente (solo con i dati base che ci servono per la recensione)
				Cliente autore = new Cliente();
				autore.setId(rs.getInt("id_cliente"));
				autore.setNome(rs.getString("nome"));
				autore.setCognome(rs.getString("cognome"));

				// Ricreiamo l'oggetto Feedback
				Feedback feedback = new Feedback();
				feedback.setId(rs.getInt("id"));
				feedback.setCliente(autore);
				feedback.setVoto(rs.getInt("voto"));
				feedback.setCommento(rs.getString("commento"));

				listaFeedback.add(feedback);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.getInstance().closeConnection(conn);
		}

		return listaFeedback;
	}

}
