package it.uniroma3.siw.spring.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Ordine;
import it.uniroma3.siw.spring.repository.OrdineRepository;

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
	

}
