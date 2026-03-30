package unipv.barbershop.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.FileInputStream;
import java.util.Properties;
public class DBConnection {
	// 1. L'unica istanza vivente della nostra classe (Il Singleton)
		private static DBConnection instance = null;

		// 2. Lo "stato" dell'oggetto (Variabili d'istanza, rigorosamente private)
		private String driver;
		private String urlBase;
		private String user;
		private String pass;

		// 3. Il Costruttore: legge il file UNA SOLA VOLTA quando l'oggetto nasce!
		private DBConnection() {
			try {
				Properties props = new Properties();
				FileInputStream fileIn = new FileInputStream("db.properties");
				props.load(fileIn);
				fileIn.close();

				// Salviamo i dati dentro le variabili del nostro oggetto (this)
				this.driver = props.getProperty("db.driver", "com.mysql.cj.jdbc.Driver");
				this.urlBase = props.getProperty("db.url"); 
				this.user = props.getProperty("db.user");
				this.pass = props.getProperty("db.password");

				// Carichiamo il driver in memoria una volta sola
				Class.forName(this.driver); 

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Errore fatale: impossibile leggere db.properties nel costruttore!");
			}
		}

		// 4. Metodo per recuperare l'unico oggetto DBConnection
		public static DBConnection getInstance() {
			if (instance == null) {
				instance = new DBConnection(); // Qui nasce l'oggetto e legge il file
			}
			return instance;
		}

		// ==========================================================
		// 5. METODI DELL'OGGETTO (Nota: NON c'è più la parola "static"!)
		// ==========================================================

		// Metodo per APRIRE la connessione
		public Connection startConnection(String schema) {
			try {
				// Usa le MIE variabili d'istanza (this) per connettersi
				return DriverManager.getConnection(this.urlBase + schema, this.user, this.pass); 
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}

		// Metodo per CHIUDERE la connessione
		public void closeConnection(Connection conn) {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
}

