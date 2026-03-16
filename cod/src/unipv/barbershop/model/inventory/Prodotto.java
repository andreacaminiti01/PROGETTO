package unipv.barbershop.model.inventory;

//Aggiungere Marca e Prezzo?

public class Prodotto {
	private int id;
	private String nome;
	private int quantitaInScorta;
	
	public Prodotto(String nome, int quantitaInScorta) {
		super();
		this.setNome(nome);
		this.setQuantitaInScorta(quantitaInScorta);
	}
	
	public Prodotto() {
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
		if (nome == null || nome.trim().isEmpty()) {
	        throw new IllegalArgumentException("Il nome del prodotto non può essere vuoto.");
	    }
		this.nome = nome;
	}
	
	
	public int getQuantitaInScorta() {
		return quantitaInScorta;
	}
	
	public void setQuantitaInScorta(int quantitaInScorta) {
		if (quantitaInScorta < 0) {
	        throw new IllegalArgumentException("La quantità in scorta non può essere negativa.");
	    }
		this.quantitaInScorta = quantitaInScorta;
	}
	
	public boolean isEsaurito() {
        return this.quantitaInScorta <= 0;
    }

    public void riduciScorta(int quantitaUsata) {
        if (this.quantitaInScorta >= quantitaUsata) {
            this.quantitaInScorta -= quantitaUsata;
        }
    }
	
	//Aggiunti metodi logici per prodotti esauriti e per prodotti utilizzati
	
	
	
	
	
	
	
	
	
	
}
