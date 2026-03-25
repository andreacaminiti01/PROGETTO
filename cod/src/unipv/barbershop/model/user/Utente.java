package unipv.barbershop.model.user;
import unipv.barbershop.model.user.exception.EmptyFieldException;
import unipv.barbershop.model.user.exception.InvalidFormatException;
/**
 * Superclasse astratta che rappresenta un utente generico del sistema.
 * Contiene gli attributi comuni a tutti gli attori (Cliente, Barbiere, Amministratore).
 * Questa classe non può essere istanziata direttamente.
 * */

//abctract cosi nessuno potrà creare "Utente" generico visto che 
//avremo cliente per prenotare o barbiere a lavorarci

//modifica costruttore per tutte le implementazioni delle eccezioni NON controllate(Runtimme)
//cioè viene sollevata quando il chiamante passa un argomento inappropriato

//modifica dei set per implementare queste eccezioni non Controllate 

public abstract class Utente {
	private int id;
	private String nome;
	private String cognome;
	private String email;
	private String password;

	public Utente() {
		super();
	}

	public Utente(String nome, String cognome, String email, String password) {
		super();
		this.setNome(nome);
		this.setCognome(cognome);
		this.setEmail(email);
		this.setPassword(password);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}



	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		if (nome == null || nome.trim().isEmpty()) {
			throw new EmptyFieldException ("Il nome non può essere vuoto.");
		}
		this.nome = nome;
	}



	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		if (cognome == null || cognome.trim().isEmpty()) {
			throw new EmptyFieldException("Il cognome non può essere vuoto.");
		}
		this.cognome = cognome;
	}



	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		// Controllo base: se l'email è null, vuota, o non contiene la "@", lancio l'eccezione
		if (email == null || email.trim().isEmpty() || !email.contains("@")) {
			// Uso l'eccezione non controllata (Runtime) suggerita dal professore
			throw new InvalidFormatException("Formato email non valido: " + email);
		}
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	// Metodo di base: di default un utente generico non ha telefono
	public String getTelefono() {
		return null; 
	}

	// Metodo di base: facciamo restituire un ruolo generico 
	// (Se la classe Utente è "abstract", puoi fare direttamente un metodo astratto!)
	public String getRuolo() {
		return "UTENTE"; 
	}

	public void setPassword(String password) {
		// Controllo: la password non deve essere nulla, vuota, o più corta di 6 caratteri
		if (password == null || password.trim().isEmpty() || password.length() < 6) {
			// Genera l'eccezione perché l'argomento passato è inappropriato
			throw new InvalidFormatException("Errore: la password non può essere vuota e deve avere almeno 6 caratteri.");
		}
		this.password = password;
	}
	
	public abstract String getTipoDatabase(); 
	// Restituirà "CLIENTE" o "AMMINISTRATORE"

	public abstract String getDettaglioRuolo(); 
	// Restituirà null per il cliente, e il ruolo vero e proprio per l'admin











}
