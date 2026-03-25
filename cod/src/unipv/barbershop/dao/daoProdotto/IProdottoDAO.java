package unipv.barbershop.dao.daoProdotto;
import unipv.barbershop.model.inventory.Prodotto;
import java.util.List;


public interface IProdottoDAO {
	// Inserisce un nuovo prodotto nel database
	boolean inserisciProdotto(Prodotto prodotto);

	// Recupera tutto l'inventario dal database
	List<Prodotto> recuperaTuttiIProdotti();

	// Aggiorna la quantità in scorta di un prodotto specifico
	boolean aggiornaScorta(int id, int nuovaQuantita);
}
