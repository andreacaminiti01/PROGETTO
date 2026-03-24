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
		// TODO Auto-generated method stub
		return "CLIENTE";
	}



	@Override
	public String toString() {
		return "Cliente:" + getNome() + "" + getCognome() + "(" + getEmail() +")";
	}





















}
