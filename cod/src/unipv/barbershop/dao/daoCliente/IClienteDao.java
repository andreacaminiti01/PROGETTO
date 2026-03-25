package unipv.barbershop.dao.daoCliente;

import unipv.barbershop.model.user.Cliente;

public interface IClienteDao {

	//Metodo per registrare un nuovo cliente
	void inserisciCliente(Cliente cliente);
	
	//metodo per fare login. Se fallsice, lancera l'eccezione
	Cliente login(String email, String password);
	
}
