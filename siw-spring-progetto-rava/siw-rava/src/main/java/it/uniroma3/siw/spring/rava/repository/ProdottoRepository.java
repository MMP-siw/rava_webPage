package it.uniroma3.siw.spring.rava.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.rava.model.Prodotto;

/*
 * Repo per i prodotti
 * @Matteo
 */
public interface ProdottoRepository extends CrudRepository<Prodotto, Long>
{
	
}
