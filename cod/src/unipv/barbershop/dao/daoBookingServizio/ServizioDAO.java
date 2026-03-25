package unipv.barbershop.dao.daoBookingServizio;

import unipv.barbershop.database.DBConnection;
import unipv.barbershop.model.booking.Servizio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServizioDAO implements IServizioDAO {

	private String schema;
	private Connection conn;

	public ServizioDAO() {
		super();
		this.schema = "barbershop";
	}

	@Override
    public boolean inserisciServizio(Servizio servizio) {
        PreparedStatement ps = null;
        boolean esito = false;

        try {
            this.conn = DBConnection.startConnection(this.conn, this.schema);
            String query = "INSERT INTO servizi (nome, prezzo, durataMinuti) VALUES (?, ?, ?)";
            ps = this.conn.prepareStatement(query);

            ps.setString(1, servizio.getNome());
            ps.setDouble(2, servizio.getPrezzo());
            ps.setInt(3, servizio.getDurataMinuti());

            ps.executeUpdate();
            esito = true;
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // SICUREZZA ASSOLUTA: Chiudiamo sempre tutto!
            try { if (ps != null) ps.close(); } catch (SQLException e) {}
            DBConnection.closeConnection(this.conn);
        }
        return esito;
    }

    @Override
    public List<Servizio> recuperaTuttiIServizi() { // Modificato in List<Servizio>
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Servizio> listino = new ArrayList<>();

        try {
            this.conn = DBConnection.startConnection(this.conn, this.schema);
            String query = "SELECT * FROM servizi";
            ps = this.conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                Servizio s = new Servizio();
                s.setId(rs.getInt("id"));
                s.setNome(rs.getString("nome"));
                s.setPrezzo(rs.getDouble("prezzo"));
                s.setDurataMinuti(rs.getInt("durataMinuti"));
                listino.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (ps != null) ps.close(); } catch (SQLException e) {}
            DBConnection.closeConnection(this.conn);
        }
        return listino;
    }
}