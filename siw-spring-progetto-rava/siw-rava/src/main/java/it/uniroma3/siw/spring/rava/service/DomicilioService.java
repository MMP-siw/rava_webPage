package it.uniroma3.siw.spring.rava.service;

import java.util.List; 
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.rava.model.Cliente;
import it.uniroma3.siw.spring.rava.model.Domicilio;
import it.uniroma3.siw.spring.rava.repository.DomicilioRepository;

@Service
public class DomicilioService {

	@Autowired
	private DomicilioRepository domRep;
	
	/*
	 * Salvataggio nel db di un domicilio
	 */
	@Transactional
	public Domicilio inserisci(Domicilio dom) 
	{
		return this.domRep.save(dom);
		
	}
	@Transactional
	public void elimina(Domicilio dom) 
	{
		this.domRep.deleteById(dom.getId());
	}
	/*
	 * Ricerca di un domicilio per id
	 * Potrebbe essere migiorata andando a effettare la ricerca solo per i domicili del cliente loggato
	 * Necessario per caso d'uso @NuovoOrdineDomicilio passo @SelezionaDomicilio
	 */
	@Transactional
	public Domicilio domicilioPerId(Long id) {
		Optional<Domicilio> optional = this.domRep.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}
	/*
	 * Si cercano tutti i domicili relativi a uno specifico utente, ovvero quello loggato
	 * che sta piazzando l'ordine
	 * Necessario per caso d'uso @NuovoOrdineDomicilio passo @SelezionaDomicilio
	 */
	@Transactional
	public List<Domicilio> domiciliPerUtente(Cliente c)
	{
		return this.domRep.findByUtente(c);			//è una costum query
	}
	
	/*
	 * Non necessario poichè la ricerca dei domicili è relativa al singolo utente.
	 * La utilizzo per il tes
	 */
	@Transactional
	public List<Domicilio> tutti()
	{
		return (List<Domicilio>) this.domRep.findAll();
	}
	
}
