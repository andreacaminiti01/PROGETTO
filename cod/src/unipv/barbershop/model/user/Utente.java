package unipv.barbershop.model.user;

/**
 * Superclasse astratta che rappresenta un utente generico del sistema.
 * Contiene gli attributi comuni a tutti gli attori (Cliente, Barbiere, Amministratore).
 * Questa classe non può essere istanziata direttamente.
 * */

//abctract cosi nessuno potrà creare "Utente" generico visto che 
//avremo cliente per prenotare o barbiere a lavorarci

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
		this.nome = nome;
		this.cognome = cognome;
		this.email = email;
		this.password = password;
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
		this.nome = nome;
	}


	
	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}


	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}


	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}











}
