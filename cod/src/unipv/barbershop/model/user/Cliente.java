package unipv.barbershop.model.user;

public class Cliente extends Utente {
	private String telefono;

	public Cliente() {
		super();
	}

	

	public Cliente(String nome, String cognome, String email, String password, String telefono) {
		super(nome, cognome, email, password);
		this.setTelefono(telefono);
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	@Override
	public String getRuolo() {
		
		return "CLIENTE";
	}



	@Override
	public String toString() {
		return "Cliente:" + getNome() + "" + getCognome() + "(" + getEmail() +")";
	}


	@Override
	public String getTipoDatabase() {
	    return "CLIENTE";
	}

	@Override
	public String getDettaglioRuolo() {
	    return null; // Il cliente non ha un ruolo speciale
	}


















}
