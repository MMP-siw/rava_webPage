package it.uniroma3.siw.spring.rava.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import it.uniroma3.siw.spring.rava.model.Ordine;
import it.uniroma3.siw.spring.rava.model.Prodotto;
import it.uniroma3.siw.spring.rava.service.OrdineService;

@Controller
@SessionAttributes("ordine,prodotto")		//definiamo che ordine è un oggetto del modello, cosi da renderlo persisitene nella sessione
public class OrdineController 
{
	@Autowired											
	private OrdineService ordineService;		//utilizzato per invocare i metodi di ordineRepo
	private final Logger logger = LoggerFactory.getLogger(this.getClass());	//per le stampe di log
	
	
	
	
	/*
	 * è il metodo inziale che permette di iniziare un nuovo ordine
	 * Caso d'uso @NuovoOrdineDomicilio passo @CreaNuovoOrdine
	 *Non si necessita la creazione dell'oggetto ordina, poichè è creato in automatico in quanto @ModelAtrribute
	 */
	@RequestMapping(value="/nuovoOrdine", method=RequestMethod.GET)
	public String creaNuovoOrdine(Model model, @ModelAttribute("ordine") Ordine ordine)
	{
		model.addAttribute("ordine",ordine);
		return "selezionaProdotto.html";
	}
	/*
	 * Inserimento di un prodotto nell'ordine
	 *DA TROVARE L'ERRRORE
	 */
	@RequestMapping(value="/prodotto/inserisci", method=RequestMethod.GET)
	public String addProdottoInOrdine(Model model, @ModelAttribute("ordine") Ordine ordine)
	{
		Prodotto prodotto=(Prodotto)model.getAttribute("prodotto");	//prendo il prodotto selezionato
		logger.debug("Info sul prodotto inserito"+ prodotto.toString());
		ordine.creaLineaOrdine(prodotto, 1);				//creo la linea d'ordine e inserisco il prodotto
		model.addAttribute("ordine", ordine);
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
	/*
	 * Creo un metodo per la creazione nel model di un oggetto Ordine
	 * Al momento dell'attivazione del server, inserira un oggetto ordine nel model
	 */
	@ModelAttribute("ordine")
	public Ordine ordineMet()
	{
		return new Ordine();
	}
	
	
}
