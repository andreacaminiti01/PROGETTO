package it.unipv.progetto.model;

public class Feedback {
	private int id;
	private Cliente cliente;
	private Prenotazione prenotazioneRiferimento;
	private int voto;
	private String commento;
	
	public Feedback(Cliente cliente, Prenotazione prenotazioneRiferimento, int voto, String commento) {
		super();
		this.cliente = cliente;
		this.prenotazioneRiferimento = prenotazioneRiferimento;
		this.voto = voto;
		this.commento = commento;
	}

	public Feedback() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Prenotazione getPrenotazioneRiferimento() {
		return prenotazioneRiferimento;
	}

	public void setPrenotazioneRiferimento(Prenotazione prenotazioneRiferimento) {
		this.prenotazioneRiferimento = prenotazioneRiferimento;
	}

	public int getVoto() {
		return voto;
	}

	public void setVoto(int voto) {
		this.voto = voto;
	}

	public String getCommento() {
		return commento;
	}

	public void setCommento(String commento) {
		this.commento = commento;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
