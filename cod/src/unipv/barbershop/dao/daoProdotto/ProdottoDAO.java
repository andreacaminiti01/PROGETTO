package unipv.barbershop.dao.daoProdotto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import unipv.barbershop.database.DBConnection;
import unipv.barbershop.model.inventory.Prodotto;

public class ProdottoDAO implements IProdottoDAO {

	private String schema;
	private Connection conn;
	
	

	public ProdottoDAO(String schema) {
		super();
		this.schema = "barbershop";
	}

	@Override
	public boolean inserisciProdotto(Prodotto prodotto) {
		conn = DBConnection.startConnection(conn, schema);
		PreparedStatement st1 = null;
		boolean esito = false;

		try {
			String query = "INSERT INTO prodotti (nome, quantitaInScorta) VALUES (?, ?)";
			st1 = conn.prepareStatement(query);
			st1.setString(1, prodotto.getNome());
			st1.setInt(2, prodotto.getQuantitaInScorta());

			st1.executeUpdate();
			esito = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		DBConnection.closeConnection(conn);
		return esito;

	}

	@Override
	public ArrayList<Prodotto> recuperaTuttiIProdotti() {
		ArrayList<Prodotto> inventario = new ArrayList<>();
		conn = DBConnection.startConnection(conn, schema);
		PreparedStatement st1 = null;
		ResultSet rs1 = null;

		try {
			String query = "SELECT * FROM prodotti";
			st1 = conn.prepareStatement(query);
			rs1 = st1.executeQuery();

			// Cicliamo sui risultati del database per creare gli oggetti Prodotto
			while (rs1.next()) {
				Prodotto p = new Prodotto();
				p.setId(rs1.getInt("id"));
				p.setNome(rs1.getString("nome"));
				p.setQuantitaInScorta(rs1.getInt("quantitaInScorta"));
				inventario.add(p);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		DBConnection.closeConnection(conn);
		return inventario;
	}

	@Override
	public boolean aggiornaScorta(int id, int nuovaQuantita) {
		conn = DBConnection.startConnection(conn, schema);
		PreparedStatement st1 = null;
		boolean esito = false;

		try {
			String query = "UPDATE prodotti SET quantitaInScorta = ? WHERE id = ?";
			st1 = conn.prepareStatement(query);
			st1.setInt(1, nuovaQuantita);
			st1.setInt(2, id);

			st1.executeUpdate();
			esito = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		DBConnection.closeConnection(conn);
		return esito;
	}

}
