package it.uniroma3.siw.spring.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Cliente;
import it.uniroma3.siw.spring.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepo;
	
	/*
	 * Memorizzazione di un cliente all'interno del database
	 */
	@Transactional
	public void inserisci(Cliente cliente) 
	{
		this.clienteRepo.save(cliente);
	
		
	}

}
