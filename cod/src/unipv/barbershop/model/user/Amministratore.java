package unipv.barbershop.model.user;

public class Amministratore extends Utente {
	private String ruolo;
	
	public Amministratore() {
		super();
	}

	public Amministratore(String nome, String cognome, String email, String password, String ruolo) {
		super(nome, cognome, email, password);
		this.ruolo= ruolo;
	}

	public String getRuolo() {
		return"AMMINISTRATORE";
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}
	
	@Override
	public String getTipoDatabase() {
	    return "AMMINISTRATORE";
	}

	@Override
	public String getDettaglioRuolo() {
	    return this.ruolo; // Restituisce la variabile ruolo (es. "Proprietario")
	}

	
	
}
