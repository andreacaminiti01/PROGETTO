package unipv.barbershop.model.staff;

public class Barbiere {
	private int id;
	private String nome;
	private String cognome;
	private String specializzazione;
	
	public Barbiere(String nome, String cognome, String specializzazione) {
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.specializzazione = specializzazione;
	}

	public Barbiere() {
		super();
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
	
	public String getSpecializzazione() {
        return specializzazione;
    }

    public void setSpecializzazione(String specializzazione) {
        this.specializzazione = specializzazione;
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
