package it.uniroma3.siw.spring.rava.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.rava.model.Cliente;
import it.uniroma3.siw.spring.rava.model.Ordine;

public interface OrdineRepository extends CrudRepository<Ordine, Long> 
{

	List<Ordine> findAllByUtente(Cliente cliente);

	List<Ordine> findAllByUtenteAndStato(Cliente cliente, String string);

}
