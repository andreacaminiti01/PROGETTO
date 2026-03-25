package unipv.barbershop.model.inventory;
import unipv.barbershop.model.inventory.Exception.ScortaInsufficienteException;
import unipv.barbershop.model.user.exception.EmptyFieldException;
import unipv.barbershop.model.inventory.Exception.NegativeValueException;

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
	        throw new EmptyFieldException("Il nome del prodotto non può essere vuoto.");
	    }
		this.nome = nome;
	}
	
	
	public int getQuantitaInScorta() {
		return quantitaInScorta;
	}
	
	public void setQuantitaInScorta(int quantitaInScorta) {
		if (quantitaInScorta < 0) {
	        throw new NegativeValueException("La quantità in scorta non può essere negativa.");
	    }
		this.quantitaInScorta = quantitaInScorta;
	}
	
	public boolean isEsaurito() {
        return this.quantitaInScorta <= 0;
    }

	public void riduciScorta(int quantitaUsata) {
        if (this.quantitaInScorta < quantitaUsata) {
            // LANCIO L'ECCEZIONE!
            throw new ScortaInsufficienteException(this.getNome());
        }
        this.quantitaInScorta -= quantitaUsata;
    }
    }
	
	//Aggiunti metodi logici per prodotti esauriti e per prodotti utilizzati
	
	
	
	
	
	
	
	
	
	

