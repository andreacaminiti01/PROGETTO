package it.unipv.progetto.model;

public class Prodotto {
	private int id;
	private String nome;
	private int quantitaInScorta;
	
	public Prodotto(String nome, int quantitaInScorta) {
		super();
		this.nome = nome;
		this.quantitaInScorta = quantitaInScorta;
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
		this.nome = nome;
	}
	
	
	public int getQuantitaInScorta() {
		return quantitaInScorta;
	}
	
	public void setQuantitaInScorta(int quantitaInScorta) {
		this.quantitaInScorta = quantitaInScorta;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
