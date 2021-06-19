package it.uniroma3.siw.spring.rava.controller.validator;

import org.springframework.stereotype.Component; 
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import static it.uniroma3.siw.spring.rava.model.Rava.cap;
import it.uniroma3.siw.spring.rava.model.Domicilio;

@Component
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
		if (!cap.contains(dom.getCap())) {
			error.rejectValue("cap", "sbagliato");
		}
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Domicilio.class.equals(clazz);
	}

}
