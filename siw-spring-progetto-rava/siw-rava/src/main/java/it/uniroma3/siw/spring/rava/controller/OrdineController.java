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

import it.uniroma3.siw.spring.rava.model.Ordine;
import it.uniroma3.siw.spring.rava.model.Prodotto;
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
	private final Logger logger = LoggerFactory.getLogger(this.getClass());	//per le stampe di log
	
	
	
	
	/*
	 * è il metodo inziale che permette di iniziare un nuovo ordine
	 * Caso d'uso @NuovoOrdineDomicilio passo @CreaNuovoOrdine
	 *Non si necessita la creazione dell'oggetto ordina, poichè è creato in automatico in quanto @ModelAtrribute
	 *Passo 1) creazione di un nuovo ordine
	 */
	@RequestMapping(value="/nuovoOrdine", method=RequestMethod.GET)
	public String creaNuovoOrdine(Model model)
	{
		Ordine ordine= new Ordine();
		model.addAttribute("ordine",ordine);
		this.ordineService.inserisci(ordine);
		return "selezionaProdotto.html";
	}
	/*
	 * Utile nel caso d'uso @OrdineADomicilio e @OrdineDaAsporto
	 * passo 2) visualizzazione dei prodotti  offert
	 * 
	 */
	@RequestMapping(value="/ordinaProdotto", method=RequestMethod.GET)
	public String prendiTuttiIProdotti(Model model, @ModelAttribute("ordine")Ordine ordine)
	{
		
		model.addAttribute("prodotti", this.prodService.tutti());
		
		logger.debug(model.getAttribute("prodotti").toString());
		return "selezionaProdotto.html";
	}
	/*
	 * 
	 * Utile nel caso d'uso @OrdineADomicilio e @OrdineDaAsporto
	 * passo 3)Scelta del prodotto
	 * 
	 */
	@RequestMapping(value = "/prodotto/{id}", method = RequestMethod.GET)
    public String getProdotto(@PathVariable("id") Long id, Model model) {
    	Prodotto prodotto = this.prodService.prodottoPerId(id);
		model.addAttribute("prodotto", prodotto);
    	logger.debug("Hai selezionato il prodotto"+ prodotto.toString() );
    	return "prodotto.html";
    }
	
	
	
	/*
	 * Inserimento di un prodotto nell'ordine
	 *passo 4)Inserimento del prodotto nell'ordine
	 */
	
	/*
	 * Problema-->Crea un ordine ad ogni nuovo prodotto selezionato
	 */
	@RequestMapping(value="/prodotto/{id}/inserisci", method=RequestMethod.GET)
	public String addProdottoInOrdine(@PathVariable("id")Long id,Model model, @ModelAttribute("ordine") Ordine ordine)
	{
		Prodotto prodotto=this.prodService.prodottoPerId(id);	//prendo il prodotto selezionato
		logger.debug("Info sul prodotto inserito"+ prodotto.toString());
		ordine.creaLineaOrdine(prodotto, 1); 			//creo la linea d'ordine e inserisco il prodotto
		this.ordineService.inserisci(ordine);
		
		model.addAttribute("prodotti",this.prodService.tutti());	//torno alla pagina di seleziona dei prodotti
		logger.debug("TOTALE DELL'ORDINE "+ordine.getId().toString()+" è: " + ordine.getTotale());
		logger.debug("INFO ORDINE: " +ordine.getLineeOrdine().toString());
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
	public Ordine ordineMod()
	{
		return new Ordine();
	}
	
	
	
	
}
