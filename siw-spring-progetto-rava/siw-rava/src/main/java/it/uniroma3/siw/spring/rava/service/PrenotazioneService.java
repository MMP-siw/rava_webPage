package it.uniroma3.siw.spring.rava.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.rava.model.Cliente;
import it.uniroma3.siw.spring.rava.model.FasciaOraria;
import it.uniroma3.siw.spring.rava.model.Prenotazione;
import it.uniroma3.siw.spring.rava.model.Slot;
import it.uniroma3.siw.spring.rava.repository.PrenotazioneRepository;
import it.uniroma3.siw.spring.rava.repository.SlotRepository;

@Service
public class PrenotazioneService {

	@Autowired
	private PrenotazioneRepository prenotazioneRepository;
	
	@Autowired 
	private SlotRepository slotRepository;
	
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
	
	@Transactional
	public List<Prenotazione> getByDataAndOrario(LocalDate data, FasciaOraria orario) {
		return (List<Prenotazione>)this.prenotazioneRepository.findByDataAndOrario(data, orario);
	}

	@Transactional
	public void cancella(Long id) {
		this.prenotazioneRepository.deleteById(id);		
	}

}
