package it.uniroma3.siw.spring.rava.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import it.uniroma3.siw.spring.rava.model.Cliente;
import it.uniroma3.siw.spring.rava.model.Credentials;
import it.uniroma3.siw.spring.rava.model.Prenotazione;
import it.uniroma3.siw.spring.rava.service.CredentialsService;
import it.uniroma3.siw.spring.rava.service.PrenotazioneService;

@Controller
@SessionAttributes("prenotazione")
public class PrenotazioneController {
	
	@Autowired
	private PrenotazioneService prenotazioneService;
	@Autowired
	private CredentialsService credentialsService;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * l'utente vuole effettuare una nuova prenotazione, viene creato il nuovo
	 * oggetto "vuoto" e restituito il form da compilare
	 */
	@RequestMapping(value="/prenota", method=RequestMethod.GET)
	public String nuovaPrenotazione(Model model) {
		
		Prenotazione prenotazione = new Prenotazione();
		logger.debug("HAI CREATO l'ORDINE NUMERO : ", prenotazione.getId());
		this.prenotazioneService.inserisci(prenotazione);
		model.addAttribute("prenotazione",prenotazione);
		return "prenotazioneForm";
		
	}
	
	/**
	 * l'utente vuole visualizzare i dati di una determinata prenotazione,
	 * anche passata
	 */
	@RequestMapping(value="/prenotazione/{id}", method=RequestMethod.GET) 
	public String getPrenotazione(Model model, @PathVariable("id") Long id) {
		Prenotazione p = this.prenotazioneService.prenotazionePerId(id);
		model.addAttribute("prenotazione", p);
		return "prenotazione";
	}
	
	/**
	 * l'utente visualizza tutte le sue prenotazioni
	 */
	@RequestMapping(value="/prenotazioni", method=RequestMethod.GET) 
	public String getPrenotazioni(Model model) {
		
		//retrieve current user
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;

		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		} else {
			username = principal.toString();
		}
		
		Credentials c = this.credentialsService.getCredentials(username);
		Cliente cliente = c.getUser();
		
		//popola pagina
		model.addAttribute("prenotazioni", this.prenotazioneService.getByCliente(cliente));
		return "prenotazioni";
	}

}
