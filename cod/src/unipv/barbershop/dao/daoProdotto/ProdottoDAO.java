package unipv.barbershop.dao.daoProdotto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List; 

import unipv.barbershop.database.DBConnection;
import unipv.barbershop.model.inventory.Prodotto;

public class ProdottoDAO implements IProdottoDAO {

    private String schema;
    private Connection conn;

    // Costruttore pulito (senza parametri inutili)
    public ProdottoDAO() {
        super();
        this.schema = "barbershop";
    }

    @Override
    public boolean inserisciProdotto(Prodotto prodotto) {
        PreparedStatement ps = null;
        boolean esito = false;

        try {
        	conn = DBConnection.getInstance().startConnection(schema);
            String query = "INSERT INTO prodotti (nome, quantitaInScorta) VALUES (?, ?)";
            ps = this.conn.prepareStatement(query);
            ps.setString(1, prodotto.getNome());
            ps.setInt(2, prodotto.getQuantitaInScorta());

            ps.executeUpdate();
            esito = true;
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // IL FINALLY SALVA LA VITA AL DATABASE!
            try { if (ps != null) ps.close(); } catch (SQLException e) {}
            DBConnection.getInstance().closeConnection(conn);
        }
        return esito;
    }

    @Override
    public List<Prodotto> recuperaTuttiIProdotti() { // Usa List, non ArrayList!
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Prodotto> inventario = new ArrayList<>();

        try {
        	conn = DBConnection.getInstance().startConnection(schema);
            String query = "SELECT * FROM prodotti";
            ps = this.conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                Prodotto p = new Prodotto();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setQuantitaInScorta(rs.getInt("quantitaInScorta"));
                inventario.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (ps != null) ps.close(); } catch (SQLException e) {}
            DBConnection.getInstance().closeConnection(conn);
        }
        return inventario;
    }

    @Override
    public boolean aggiornaScorta(int id, int nuovaQuantita) {
        PreparedStatement ps = null;
        boolean esito = false;

        try {
        	conn = DBConnection.getInstance().startConnection(schema);
            String query = "UPDATE prodotti SET quantitaInScorta = ? WHERE id = ?";
            ps = this.conn.prepareStatement(query);
            ps.setInt(1, nuovaQuantita);
            ps.setInt(2, id);

            ps.executeUpdate();
            esito = true;
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (ps != null) ps.close(); } catch (SQLException e) {}
            DBConnection.getInstance().closeConnection(conn);
        }
        return esito;
    }
}