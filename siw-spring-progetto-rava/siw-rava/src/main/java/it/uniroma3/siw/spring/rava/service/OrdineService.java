package it.uniroma3.siw.spring.rava.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.rava.model.Ordine;
import it.uniroma3.siw.spring.rava.repository.OrdineRepository;

@Service
public class OrdineService 
{
	@Autowired
	private OrdineRepository ordineRepo;

	/*
	 * Rende persistente un ordine effettuato
	 */
	@Transactional
	public void inserisci(Ordine ordine) 
	{
		this.ordineRepo.save(ordine);
		
	}
	
	@Transactional 
	public Ordine getOrdineById (Long id) {
		Optional<Ordine> ris = this.ordineRepo.findById(id);
		return ris.orElse(null);
	}

}
