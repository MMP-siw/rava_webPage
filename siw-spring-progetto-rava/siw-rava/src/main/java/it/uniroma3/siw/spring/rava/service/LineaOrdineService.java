package it.uniroma3.siw.spring.rava.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.rava.model.LineaOrdine;
import it.uniroma3.siw.spring.rava.repository.LineaOrdineRepository;

@Service
public class LineaOrdineService 
{
	@Autowired
	private LineaOrdineRepository lineaRepo;
	
	@Transactional
	public LineaOrdine inserisci(LineaOrdine lio)
	{
		return this.lineaRepo.save(lio);
	}

}
