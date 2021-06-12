package it.uniroma3.siw.spring.rava.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.rava.model.Cliente;
import it.uniroma3.siw.spring.rava.model.Prenotazione;

public interface PrenotazioneRepository extends CrudRepository<Prenotazione,Long> {
	
	public List<Prenotazione> findByCliente(Cliente c);

}
