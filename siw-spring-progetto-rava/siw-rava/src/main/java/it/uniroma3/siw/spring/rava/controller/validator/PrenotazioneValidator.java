package it.uniroma3.siw.spring.rava.controller.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PrenotazioneValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * l'oggetto si occupa di controllare che la prenotazione sia valida, ovvero controllare che 
	 * il numero massimo di persone permesso all'interno del ristorante non venga superato con la nuova prenotazione
	 */

}
