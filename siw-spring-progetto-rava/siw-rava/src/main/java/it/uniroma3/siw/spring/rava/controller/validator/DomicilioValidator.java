package it.uniroma3.siw.spring.rava.controller.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.spring.rava.model.Domicilio;

public class DomicilioValidator implements Validator {

	@Override
	public void validate(Object o, Errors error) {
		Domicilio dom = (Domicilio) o;
		if (dom.getTipo().isEmpty()) {

			error.rejectValue("tipo", "required");
		}
		if (dom.getIndirizzo().isEmpty()) {
			error.rejectValue("indirizzo", "required");
		}
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Domicilio.class.equals(clazz);
	}

}
