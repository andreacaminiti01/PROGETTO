package unipv.barbershop.model.booking;


import unipv.barbershop.model.user.Cliente;
import unipv.barbershop.model.staff.Barbiere; 
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



public class Prenotazione {
	private int id;
	private Cliente cliente;
	private Barbiere barbiere;
	private LocalDateTime dataOra; //data e ora insieme per controllare meglio se un barbiere è libero
	private List<Servizio> serviziScelti; //con lista permette di prenotare piu cose insieme come Taglio+Barba
	
	public Prenotazione(Cliente cliente, Barbiere barbiere, LocalDateTime dataOra) {
		super();
		this.cliente = cliente;
		this.barbiere = barbiere;
		this.dataOra = dataOra;
		this.serviziScelti = new ArrayList<>();
	}

	public Prenotazione() {
		super();
		this.serviziScelti = new ArrayList<>();
	}
	
	public void addServizio(Servizio s) { //per capire i servizi scelti dal cliente
		this.serviziScelti.add(s);
	}
	
	public double calcolaPrezzoTotale() {
		double totale = 0;
		for ( Servizio s : serviziScelti) {
			totale += s.getPrezzo();
		}
		return totale;
	}
	
	public int calcolareDurataTotaleMinuti() {
		int durataTotale = 0;
		for(Servizio s : serviziScelti) {
			durataTotale += s.getDurataMinuti();
			}
		return durataTotale;
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
	
	public LocalDateTime getDataOra() {
		return dataOra;
	}

	public void setDataOra(LocalDateTime dataOra) {
		this.dataOra = dataOra;
	}

	public List<Servizio> getServiziScelti() {
		return serviziScelti;
	}

	public void setServiziScelti(List<Servizio> serviziScelti) {
		this.serviziScelti = serviziScelti;
	}

	/**
     * Calcola dinamicamente l'ora di fine sommando la durata del servizio all'ora di inizio.
     * Molto utile per il Check Disponibilità.
     */
    //public LocalTime getOraFine() {
     //   if (oraInizio != null && servizio != null) {
      //      return oraInizio.plusMinutes(servizio.getDurataMinuti());
      //  }
      //  return null;
    //}
	
	//ho unito data e ora mi sembra piu comodo pero se in caso 
	//ritorniamo come prima questa non l'ho eliminata ma messa sotto commento
	
	
	
	
}
