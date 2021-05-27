package it.uniroma3.siw.spring.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.model.Prodotto;

/*
 * Repo per i prodotti
 * @Matteo
 */
public interface ProdottoRepository extends CrudRepository<Prodotto, Long>
{
	
}
