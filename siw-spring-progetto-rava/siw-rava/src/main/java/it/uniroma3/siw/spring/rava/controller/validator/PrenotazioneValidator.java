package it.uniroma3.siw.spring.rava.controller.validator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import static it.uniroma3.siw.spring.rava.model.Rava.postiMax;
import it.uniroma3.siw.spring.rava.model.Prenotazione;
import it.uniroma3.siw.spring.rava.model.Rava;
import it.uniroma3.siw.spring.rava.service.PrenotazioneService;

@Component
public class PrenotazioneValidator implements Validator {
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)

	/**
	 * l'oggetto si occupa di controllare che la prenotazione sia valida, ovvero controllare che 
	 * il numero massimo di persone permesso all'interno del ristorante non venga superato con la nuova prenotazione
	 */
	
	@Autowired
	private PrenotazioneService prenotazioneService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Prenotazione.class.equals(clazz);
	}

	@Override
	public void validate(Object o, Errors errors) {
		Prenotazione prenotazione = (Prenotazione) o;
		LocalDate data = prenotazione.getData();
		
		//controlla se c'è posto sufficiente per tutti i prenotanti
		List<Prenotazione> tutte = prenotazioneService.getByDataAndOrario(data, prenotazione.getOrario());
		int totaleDataOrario = 0;
		for (Prenotazione p: tutte) {
			totaleDataOrario += p.getNumeroPersone();
		}
		totaleDataOrario += prenotazione.getNumeroPersone();
		if (totaleDataOrario > Rava.postiMax) {
			errors.rejectValue("orario", "numero");
		}
		
		//controlla che la data scelta sia uguale o maggiore di quella di oggi
		if (data.isBefore(LocalDate.now())) {
			errors.rejectValue("data", "indietro");
		}
	
	}

}
