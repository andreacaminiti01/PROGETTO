package unipv.barbershop.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBConnection {
	// Parametri di connessione al database
	private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver"; 
	private static final String DB_URL = "jdbc:mysql://localhost:3306/"; 
	private static final String USERNAME = "root"; 

	// ATTENZIONE: INSERISCI QUI TRA LE VIRGOLETTE LA PASSWORD DI MYSQL WORKBENCH
	private static final String PASSWORD = "MysqlDB2025!"; 

	// Metodo per APRIRE la connessione
	public static Connection startConnection(Connection conn, String schema) {
		if (isOpen(conn)) {
			closeConnection(conn);
		}
		try {
			Class.forName(DB_DRIVER); // Carica il driver in memoria
			conn = DriverManager.getConnection(DB_URL + schema, USERNAME, PASSWORD); 
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// Metodo per VERIFICARE se è aperta
	public static boolean isOpen(Connection conn) {
		return conn != null;
	}

	// Metodo per CHIUDERE la connessione
	public static Connection closeConnection(Connection conn) {
		if (!isOpen(conn)) {
			return null;
		}
		try {
			conn.close();
			conn = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
}

