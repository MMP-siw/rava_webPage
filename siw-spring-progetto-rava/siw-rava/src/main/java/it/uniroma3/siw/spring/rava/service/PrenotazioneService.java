package it.uniroma3.siw.spring.rava.service;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.rava.model.Cliente;
import it.uniroma3.siw.spring.rava.model.Prenotazione;
import it.uniroma3.siw.spring.rava.repository.PrenotazioneRepository;

@Service
public class PrenotazioneService {

	@Autowired
	private PrenotazioneRepository prenotazioneRepository;
	
	@Transactional
	public Prenotazione inserisci(Prenotazione prenotazione) {
		return this.prenotazioneRepository.save(prenotazione);
	}

	@Transactional
	public Prenotazione prenotazionePerId(Long id) {
		Optional<Prenotazione> optional = prenotazioneRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}

	@Transactional
	public List<Prenotazione> getByCliente(Cliente cliente) {
		return (List<Prenotazione>)this.prenotazioneRepository.findByCliente(cliente);
	}

}
