package it.uniroma3.siw.spring.rava.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import it.uniroma3.siw.spring.rava.model.Prodotto;
import it.uniroma3.siw.spring.rava.service.ProdottoService;

@Controller
@SessionAttributes("prodotto")
public class ProdottoController 
{
	@Autowired
	private ProdottoService prodottoSer;	
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());	//per le stampe di log
	
	//Il validator non serve ancora
	
//	/*
//	 * Utile nel caso d'uso @OrdineADomicilio e @OrdineDaAsporto
//	 * 
//	 */
//	@RequestMapping(value="/ordinaProdotto", method=RequestMethod.GET)
//	public String prendiTuttiIProdotti(Model model)
//	{
//		
//		model.addAttribute("prodotti", this.prodottoSer.tutti());
//		logger.debug(model.getAttribute("prodotti").toString());
//		return "selezionaProdotto.html";
//	}
//	/*
//	 * 
//	 * Utile nel caso d'uso @OrdineADomicilio e @OrdineDaAsporto
//	 * 
//	 */
//	@RequestMapping(value = "/prodotto/{id}", method = RequestMethod.GET)
//    public String getPersona(@PathVariable("id") Long id, Model model) {
//    	Prodotto prodotto = this.prodottoSer.prodottoPerId(id);
//		model.addAttribute("prodotto", prodotto);
//    	logger.debug("Hai selezionato il prodotto"+ prodotto.toString() );
//    	return "prodotto.html";
//    }	
	
	

}
