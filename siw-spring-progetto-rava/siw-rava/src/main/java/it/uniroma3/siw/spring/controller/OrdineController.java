package it.uniroma3.siw.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.spring.controller.validator.OrdineValidator;
import it.uniroma3.siw.spring.model.Ordine;
import it.uniroma3.siw.spring.service.OrdineService;

@Controller
public class OrdineController 
{
	@Autowired											
	private OrdineService ordineService;		//utilizzato per invocare i metodi di ordineRepo
	
	@Autowired
	private OrdineValidator ordineValidator;
	
	/*
	 * è il metodo inziale che permette di iniziare un nuovo ordine
	 * Caso d'uso @NuovoOrdineDomicilio passo @CreaNuovoOrdine
	 */
	@RequestMapping(value="/nuovoOrdine", method=RequestMethod.GET)
	public String creaNuovoOrdine(Model model)
	{
		model.addAttribute("ordine",new Ordine());
		return "selezionaProdotto.html";
	}
	
	@RequestMapping(value="/domicilio", method=RequestMethod.GET)		//nel templeat va creato il link sul bottone
																				//th:href="@{/domicilio}
	public String setModalitaDiConsegna(Model model)
	{
		Ordine o= (Ordine)model.getAttribute("ordine");	//prendo l'oggetto ordine
		o.setTipo("domicilio");							//ne setto il tipo a domicilio
		model.addAttribute("ordine",o);					//lo inserisco aggionrato nel model
		return "selezionaDomicilio.html";				//vado su selezionaDomicilio.html
	}
	

}
