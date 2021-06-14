package it.uniroma3.siw.spring.rava.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.rava.model.Cliente;
import it.uniroma3.siw.spring.rava.model.Domicilio;

public interface DomicilioRepository extends CrudRepository<Domicilio, Long> 
{
	
	
	
	public List<Domicilio>findByUtente(Cliente c);
	
	
	

}
