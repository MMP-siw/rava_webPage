package it.uniroma3.siw.spring.rava.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.uniroma3.siw.spring.rava.model.Cliente;
import it.uniroma3.siw.spring.rava.model.Credentials;
import it.uniroma3.siw.spring.rava.repository.ClienteRepository;
import it.uniroma3.siw.spring.rava.repository.CredentialsRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepo;
	
	@Autowired
	private CredentialsRepository credRepo;
	
	/*
	 * Memorizzazione di un cliente all'interno del database
	 */
	@Transactional
	public Cliente saveCliente(Cliente cliente) 
	{
		return this.clienteRepo.save(cliente);
	}
	
	@Transactional
    public Cliente getCliente(Long id) {
        Optional<Cliente> result = this.clienteRepo.findById(id);
        return result.orElse(null);
    }
	
	 @Transactional
	    public List<Cliente> getAllClienti() {
	        List<Cliente> result = new ArrayList<>();
	        Iterable<Cliente> iterable = this.clienteRepo.findAll();
	        for(Cliente cliente : iterable)
	            result.add(cliente);
	        return result;
	    }
}
