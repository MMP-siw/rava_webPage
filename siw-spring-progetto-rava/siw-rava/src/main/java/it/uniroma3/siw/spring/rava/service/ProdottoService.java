package it.uniroma3.siw.spring.rava.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.spring.rava.model.Prodotto;
import it.uniroma3.siw.spring.rava.repository.ProdottoRepository;

@Service
public class ProdottoService 
{
	@Autowired
	private ProdottoRepository prodRep;

	@Transactional
	public Prodotto inserisci(Prodotto p1) 
	{
		return this.prodRep.save(p1);

	}

	/*
	 * Necessario per la selezione del prodotto
	 * Casi d'uso @NuovoOrdineADomicilio passo @SelezionaProdotto
	 */
	@Transactional
	public Prodotto prodottoPerId(Long id) {


		Optional<Prodotto> optional = this.prodRep.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}
	/*
	 * Necessario per la visualizzazione di tutti i prodotti interni al nostro sistema
	 * Casi d'uso @NuovoOrdineADomicilio passo @SelezionaProdotto
	 */
	@Transactional
	public List<Prodotto> tutti()
	{
		return (List<Prodotto>)this.prodRep.findAll();		//ha bisogno di un cast poichè restituisce un oggetto Iterable
	}
}


