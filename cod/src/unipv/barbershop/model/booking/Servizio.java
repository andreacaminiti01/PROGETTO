package unipv.barbershop.model.booking;

public class Servizio {
	private int id;
	private String nome;   
	private double prezzo;
	private int durataMinuti;
	
	public Servizio(String nome, double prezzo, int durataMinuti) {
		super();
		this.setNome(nome);
		this.setPrezzo(prezzo);
		this.setDurataMinuti(durataMinuti);
	}
	
	public Servizio() {
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

	public double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(double prezzo) {
		if (prezzo < 0) {
	        throw new IllegalArgumentException("Il prezzo del servizio non può essere negativo.");
	    }
		this.prezzo = prezzo;
	}

	public int getDurataMinuti() {
		return durataMinuti;
	}

	public void setDurataMinuti(int durataMinuti) {
		if (durataMinuti <= 0) {
	        throw new IllegalArgumentException("La durata del servizio deve essere maggiore di zero.");
	    }
		this.durataMinuti = durataMinuti;
	}
	
	
	
	
	
	
	
	
	
	
	
}
