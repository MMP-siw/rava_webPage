package it.uniroma3.siw.spring.rava.controller.validator;

import java.time.LocalTime;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.spring.rava.model.Ordine;



@Component
public class OrdineValidator  implements Validator
{

	@Override
	public boolean supports(Class<?> clazz) {
		return Ordine.class.equals(clazz);
	}

	@Override
	public void validate(Object o, Errors errors) 
	{
		//controlla che l'orario di consegna sia almeno maggiore di 30 minuti l'ora corrente
		Ordine ordine=(Ordine)o;
		
		if(ordine.getOrarioConsegna() == null) {
			errors.rejectValue("orarioConsegna", "nonValido");
		}
		
		//verifico che il cliente abbia scelto una delle modalità definite di ritiro
		if(ordine.getTipologiaDiConsegna()==null)
		{
			errors.rejectValue("tipologiaDiConsegna","nonValido");
		}
		else
		{
			if(ordine.getTipologiaDiConsegna().equals("Pianifica"))	//il controllo si fa solo e la data va settata
			{
				LocalTime t=LocalTime.parse(ordine.getOrarioConsegna());
				if(t.isBefore(LocalTime.now().plusMinutes(30)))
				{
					errors.rejectValue("orarioConsegna", "nonValido");
				}
			}
		}
		
	}

}
