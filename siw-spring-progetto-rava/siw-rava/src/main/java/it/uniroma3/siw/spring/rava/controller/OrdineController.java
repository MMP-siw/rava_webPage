package it.uniroma3.siw.spring.rava.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;  
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.uniroma3.siw.spring.rava.model.LineaOrdine;
import it.uniroma3.siw.spring.rava.model.Ordine;
import it.uniroma3.siw.spring.rava.model.Prodotto;
import it.uniroma3.siw.spring.rava.service.LineaOrdineService;
import it.uniroma3.siw.spring.rava.service.OrdineService;
import it.uniroma3.siw.spring.rava.service.ProdottoService;

@Controller

@SessionAttributes("ordine,prodotto")		//definiamo che ordine è un oggetto del modello, cosi da renderlo persisitene nella sessione
public class OrdineController 
{
	@Autowired											
	private OrdineService ordineService;		//utilizzato per invocare i metodi di ordineRepo
	
	@Autowired
	private ProdottoService prodService;	//utilizzato per invocare i metodi di ordineProdotto
	
	@Autowired
	private LineaOrdineService linea;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());	//per le stampe di log

	
	
	
	
	
//	/*
//	 * è il metodo inziale che permette di iniziare un nuovo ordine
//	 * Caso d'uso @NuovoOrdineDomicilio passo @CreaNuovoOrdine
//	 *Non si necessita la creazione dell'oggetto ordina, poichè è creato in automatico in quanto @ModelAtrribute
//	 *Passo 1) creazione di un nuovo ordine
//	 */
//
//	/*
//	 * Utile nel caso d'uso @OrdineADomicilio e @OrdineDaAsporto
//	 * passo 2) visualizzazione dei prodotti  offert
//	 * 
//	 */
//	@RequestMapping(value="/ordinaProdotto", method=RequestMethod.GET)
//	public String prendiTuttiIProdotti(Model model, @ModelAttribute("ordine")Ordine ordine)
//	{
//		model.addAttribute("ordine",ordine);
//	
//		model.addAttribute("prodotti", this.prodService.tutti());
//		logger.debug(model.getAttribute("prodotti").toString());
//		return "selezionaProdotto.html";
//	}
//	/*
//	 * 
//	 * Utile nel caso d'uso @OrdineADomicilio e @OrdineDaAsporto
//	 * passo 3)Scelta del prodotto
//	 * 
//	 */
//	@RequestMapping(value = "/prodotto/{id}", method = RequestMethod.GET)
//    public String getProdotto(@PathVariable("id") Long id, Model model,@ModelAttribute("prodotto") Prodotto prod,
//			RedirectAttributes attributes) {
//    	prod = this.prodService.prodottoPerId(id);
//		model.addAttribute("prodotto", prod);
//		
//		attributes.addAttribute(prod);		//lo metto in sessione
//		
//    	logger.debug("Hai selezionato il prodotto"+ prod.getId()+ "  Di PREZZO  "+prod.getPrezzo());
//    	
//    	return "prodotto.html";
//    }
//	
//	
//	
//	/*
//	 * Inserimento di un prodotto nell'ordine
//	 *passo 4)Inserimento del prodotto nell'ordine
//	 */
//	
//	/*
//	 * Problema-->Crea un ordine ad ogni nuovo prodotto selezionato
//	 */
//	@RequestMapping(value="/prodotto/inserisci", method=RequestMethod.GET)
//	public String addProdottoInOrdine(Model model, @ModelAttribute("ordine") Ordine ordine,@ModelAttribute("prodotto") Prodotto prod,
//			RedirectAttributes attributes)
//	{
//		
//		logger.debug("Info sul prodotto inserito"+ prod.toString());
//		LineaOrdine lio=ordine.creaLineaOrdine(prod, 1); 			//creo la linea d'ordine e inserisco il prodotto
//		model.addAttribute("prodotti",this.prodService.tutti());	//torno alla pagina di seleziona dei prodotti
//		
//		attributes.addFlashAttribute(ordine);//per la sessione
//		
//		this.ordineService.inserisci(ordine);
//		prod=(Prodotto)attributes.getAttribute("prodotto");
//		logger.debug("PRODOTTO ID="+ prod.getId());
//		logger.debug("TOTALE DELL'ORDINE "+ordine.getId().toString()+" è: " + ordine.getTotale());
//		logger.debug("INFO ORDINE: " +ordine.getLineeOrdine().toString());
//		logger.debug("ORDINE ID= "+ordine.toString());
//		return "selezionaProdotto.html";
//		
//	}
//	
//	@RequestMapping(value="/settaDomicilio", method=RequestMethod.GET)
//	public String settaModalitàDomicilio(Model model, @ModelAttribute("ordine") Ordine ordine, RedirectAttributes attributes)
//	{
//		ordine.setTipo("Domicilio");
//		model.addAttribute(ordine);
//		attributes.addFlashAttribute(ordine);
//		logger.debug("L'ORDINE NUMERO " + ordine.getId()+ "è STATO SETTATO A  domicilio");
//		
//		return "selezionaDomicilio.html";
//	}
//	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
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
	
//	@ModelAttribute("ordine")
//	public Ordine getOrdine()
//	{
//		return new Ordine();
//	}
//	@ModelAttribute("prodotto")
//	public Prodotto getProdotto()
//	{
//		return new Prodotto();
//	}
	
	
	/*
	 * SOLUZIONE 2, senza gestione della sessione ma con l'id dell'ordine memorizzato nel url
	 */
	@RequestMapping(value="/ordinaProdotto", method=RequestMethod.GET)
	public String prendiTuttiIProdotti(Model model)
	{
		
		Ordine ordineCorrente=new Ordine();
		List<Prodotto> listaProdotti=this.prodService.tutti();
		logger.debug("HAI CREATO l'ORDINE NUMERO : ", ordineCorrente.getId());
		this.ordineService.inserisci(ordineCorrente);
		model.addAttribute("ordine",ordineCorrente);
		model.addAttribute("prodotti",listaProdotti);
		
		
		return "selezionaProdotto.html";
	}
	@RequestMapping(value = "ordine/{id}/prodotto/{id2}", method = RequestMethod.GET)
	public String getProdotto(@PathVariable("id") Long id, @PathVariable("id2")Long id2, Model model) {
		
		Ordine ordine=this.ordineService.trovaPerId(id);
		logger.debug("STAI LAVORANDO CON L'ORDINE NUMERO:"+ ordine.getId());
		Prodotto prod = this.prodService.prodottoPerId(id2);
		logger.debug("HAI SELEZIONATO IL PRODOTTO :"+ prod.getId());
		model.addAttribute("prodotto", prod);
		model.addAttribute("ordine",ordine);
		return "prodotto.html";
	}
	@RequestMapping(value="/ordine/{id}/prodotto/{id2}/inserisci", method=RequestMethod.GET)
		public String addProdottoInOrdine(Model model,@PathVariable("id") Long id, @PathVariable("id2")Long id2 )
		{
			
			Ordine ordine=this.ordineService.trovaPerId(id);
			
			
			LineaOrdine lio=ordine.creaLineaOrdine(this.prodService.prodottoPerId(id2), 1); //creo la linea d'ordine e inserisco il prodotto
			model.addAttribute("prodotti",this.prodService.tutti());		//torno alla pagina di seleziona dei prodotti
			
			lio.setOrdine(ordine);
			this.linea.inserisci(lio);
			this.ordineService.inserisci(ordine);
			
			logger.debug("TOTALE DELL'ORDINE "+ordine.getId().toString()+" è: " + ordine.getTotale());
			logger.debug("INFO ORDINE: " +ordine.getLineeOrdine().toString());
			logger.debug("ORDINE ID= "+ordine.toString());
			model.addAttribute("ordine",ordine);
			return "selezionaProdotto.html";
			
		}
	
	@RequestMapping(value="/ordine/{id}/settaDomicilio", method=RequestMethod.GET)
	public String settaModalitàDomicilio(Model model,@PathVariable("id")Long id)
	{
		Ordine ordine=this.ordineService.trovaPerId(id);
		ordine.setTipo("Domicilio");
		model.addAttribute(ordine);
		this.ordineService.inserisci(ordine);
		logger.debug("L'ORDINE NUMERO " + ordine.getId()+ "è STATO SETTATO A  domicilio");
		
		return "selezionaDomicilio.html";
	}
	
	
	
	
	
	
	
	
}
