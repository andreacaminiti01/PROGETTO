package unipv.barbershop.model.booking;
import java.time.LocalDate;
import java.time.LocalTime;

import unipv.barbershop.model.user.Cliente;



public class Prenotazione {
	private int id;
	private Cliente cliente;
	private Barbiere barbiere;
	private Servizio servizio;
	private LocalDate data;
	private LocalTime oraInizio;
	
	public Prenotazione(Cliente cliente, Barbiere barbiere, Servizio servizio, LocalDate data, LocalTime oraInizio) {
		super();
		this.cliente = cliente;
		this.barbiere = barbiere;
		this.servizio = servizio;
		this.data = data;
		this.oraInizio = oraInizio;
	}

	public Prenotazione() {
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

	public Barbiere getBarbiere() {
		return barbiere;
	}

	public void setBarbiere(Barbiere barbiere) {
		this.barbiere = barbiere;
	}

	public Servizio getServizio() {
		return servizio;
	}

	public void setServizio(Servizio servizio) {
		this.servizio = servizio;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public LocalTime getOraInizio() {
		return oraInizio;
	}

	public void setOraInizio(LocalTime oraInizio) {
		this.oraInizio = oraInizio;
	}
	
	
	/**
     * Calcola dinamicamente l'ora di fine sommando la durata del servizio all'ora di inizio.
     * Molto utile per il Check Disponibilità.
     */
    public LocalTime getOraFine() {
        if (oraInizio != null && servizio != null) {
            return oraInizio.plusMinutes(servizio.getDurataMinuti());
        }
        return null;
    }
	
	
	
	
	
	
}
