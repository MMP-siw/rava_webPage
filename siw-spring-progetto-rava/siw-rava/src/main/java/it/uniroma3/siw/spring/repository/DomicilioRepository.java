package it.uniroma3.siw.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.model.Cliente;
import it.uniroma3.siw.spring.model.Domicilio;

public interface DomicilioRepository extends CrudRepository<Domicilio, Long> 
{
	/*
	 * Definisco una query parametrica per stampare i soli domicili relativi al cliente loggato.
	 * NOTA ?1 risulta essere relativo al parametro 1 del metodo
	 * 
	 * Necessario per visualizzare i soli domicili del cliente loggato
	 * Caso d'uso @NuovoOrdineDomicilio passo @SelezionaDomicilio
	 * @Query(value="SELECT d FROM Domicilio d WHERE d.costumer= ?1")
			public List<Domicilio>findByUtente(Cliente c);
	 */
	
	
	
	public List<Domicilio>findByUtente(Cliente c);

}
