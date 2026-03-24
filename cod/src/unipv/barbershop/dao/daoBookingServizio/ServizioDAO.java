package unipv.barbershop.dao.daoBookingServizio;
import unipv.barbershop.database.DBConnection;
import unipv.barbershop.model.booking.Servizio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ServizioDAO implements IServizioDAO {

	private String schema;
	private Connection conn;

	public ServizioDAO(String schema) {
		super();
		this.schema = "barbershop";
	}

	@Override
	public boolean inserisciServizio(Servizio servizio) {
		conn = DBConnection.startConnection(conn, schema);
		PreparedStatement st1 = null;
		boolean esito = false;

		try {
			// Prepariamo la query con i punti di domanda di sicurezza
			String query = "INSERT INTO servizi (nome, prezzo, durataMinuti) VALUES (?, ?, ?)";
			st1 = conn.prepareStatement(query);

			// Riempiamo i punti di domanda con i dati dell'oggetto
			st1.setString(1, servizio.getNome());
			st1.setDouble(2, servizio.getPrezzo());
			st1.setInt(3, servizio.getDurataMinuti());

			st1.executeUpdate();
			esito = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		DBConnection.closeConnection(conn);
		return esito;
	}

	@Override
	public ArrayList<Servizio> recuperaTuttiIServizi() {
		ArrayList<Servizio> listino = new ArrayList<>();
		conn = DBConnection.startConnection(conn, schema);
		PreparedStatement st1 = null;
		ResultSet rs1 = null;

		try {
			// Chiediamo a MySQL di darci tutta la tabella servizi
			String query = "SELECT * FROM servizi";
			st1 = conn.prepareStatement(query);
			rs1 = st1.executeQuery();

			// Per ogni riga che MySQL ci restituisce, creiamo un oggetto Servizio
			while (rs1.next()) {
				Servizio s = new Servizio();
				s.setId(rs1.getInt("id"));
				s.setNome(rs1.getString("nome"));
				s.setPrezzo(rs1.getDouble("prezzo"));
				s.setDurataMinuti(rs1.getInt("durataMinuti"));
				listino.add(s);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		DBConnection.closeConnection(conn);
		return listino;
	}

}
