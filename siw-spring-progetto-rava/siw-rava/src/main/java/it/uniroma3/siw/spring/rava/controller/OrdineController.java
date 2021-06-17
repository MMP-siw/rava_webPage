package it.uniroma3.siw.spring.rava.controller;
 
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.apache.tomcat.jni.Time;
import org.slf4j.Logger;  
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import it.uniroma3.siw.spring.rava.controller.validator.OrdineValidator;
import it.uniroma3.siw.spring.rava.model.Cliente;
import it.uniroma3.siw.spring.rava.model.Credentials;
import it.uniroma3.siw.spring.rava.model.Domicilio;
import it.uniroma3.siw.spring.rava.model.LineaOrdine;
import it.uniroma3.siw.spring.rava.model.Ordine;
import it.uniroma3.siw.spring.rava.model.Prodotto;
import it.uniroma3.siw.spring.rava.service.ClienteService;
import it.uniroma3.siw.spring.rava.service.CredentialsService;
import it.uniroma3.siw.spring.rava.service.DomicilioService;
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
	
	@Autowired
	private DomicilioService domService;
	
	@Autowired
	private CredentialsService credentialsService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());	//per le stampe di log

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private OrdineValidator ordineValidator;






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
	private  Credentials getCliente() {
		//Necessito delle info dell'utente loggato per inserirle in automatico
				//retrieve current user
				Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				String username;

				if (principal instanceof UserDetails) {
					username = ((UserDetails)principal).getUsername();
				} else {
					username = principal.toString();
				}
						
				Credentials c = this.credentialsService.getCredentials(username);
				
				return c;
	}
	
	/*
	 * l'utente desidera effettuare un nuovo ordine
	 * Passo 1 ordina
	 */
	@RequestMapping(value="/ordinaProdotto", method=RequestMethod.GET)
	public String prendiTuttiIProdotti(Model model)
	{
		Credentials c=getCliente();
		//effuettuo un controllo per verificare che l'utente sia effettivamente loggato
		if(c==null)
		{

			return "error/registratiOLogga.html";
		
		}
		Cliente cliente=c.getUser();
		logger.debug("L'UTETNE LOGGATO HA ID : " +  cliente.getId());
		logger.debug("NOME: " +cliente.getNome() + "COGNOME: "+ cliente.getCognome());
				


		Ordine ordineCorrente=new Ordine();
		
		List<Prodotto> listaProdotti=this.prodService.tutti();
		logger.debug("HAI CREATO l'ORDINE NUMERO : ", ordineCorrente.getId());
		ordineCorrente.setUtente(cliente);
		cliente.addOrdine(ordineCorrente);
		
		this.ordineService.inserisci(ordineCorrente);
		model.addAttribute("ordine",ordineCorrente);
		model.addAttribute("prodotti",listaProdotti);
		this.clienteService.saveCliente(cliente);

		return "ordine/selezionaProdotto.html";
	}
	
	
	/*
	 * L'utente Seleziona il prodotto desiderato
	 * Passo 2 caso d'uso Ordina
	 */
	@RequestMapping(value = "ordine/{id}/prodotto/{id2}", method = RequestMethod.GET)
	public String getProdotto(@PathVariable("id") Long id, @PathVariable("id2")Long id2, Model model) {

		Ordine ordine=this.ordineService.trovaPerId(id);
		logger.debug("STAI LAVORANDO CON L'ORDINE NUMERO:"+ ordine.getId());
		Prodotto prod = this.prodService.prodottoPerId(id2);
		logger.debug("HAI SELEZIONATO IL PRODOTTO :"+ prod.getId());
		model.addAttribute("prodotto", prod);
		model.addAttribute("ordine",ordine);
		return "ordine/prodotto.html";
	}
	/*
	 * L'utetne inserisce il prodotto nell'ordine
	 * Passo 3 caso d'uso
	 */
	@RequestMapping(value="/ordine/{id}/prodotto/{id2}/inserisci", method=RequestMethod.GET)
	public String addProdottoInOrdine(Model model,@PathVariable("id") Long id, @PathVariable("id2")Long id2 )
	{

		Ordine ordine=this.ordineService.trovaPerId(id);
		Prodotto p=this.prodService.prodottoPerId(id2);

		LineaOrdine lio= new LineaOrdine();
		lio.setProdotto(p);
		lio.setSubTotale(p.getPrezzo());
		lio.setQuantita(1);//creo la linea d'ordine e inserisco il prodotto
		
		ordine.setTotale(ordine.getTotale() + lio.getSubTotale());
		ordine.aggoungiLineaOrdine(lio);
		lio.setOrdine(ordine);
		
		
		
		this.linea.inserisci(lio);
		this.ordineService.inserisci(ordine);

		logger.debug("TOTALE DELL'ORDINE "+ordine.getId().toString()+" è: " + ordine.getTotale());
		logger.debug("INFO ORDINE: " +ordine.getLineeOrdine().toString());
		logger.debug("TOTALE ORDINE = "+ ordine.getTotale());
		model.addAttribute("prodotti",this.prodService.tutti());		//torno alla pagina di seleziona dei prodotti

		model.addAttribute("ordine",ordine);
		return "ordine/selezionaProdotto.html";

	}

	/*
	 * L'utetnte ha terminato e la selezione dei prodotti e definisce la modalità di consegna
	 * Passo 4 caso d'uso ordina
	 * 
	 * NOTA->I domicilio che inseriamo nella view sono quelli dell'utente loggato.
	 * Per adesso per semplicità mostro tutti i domicili.
	 * Quando andremo ad implementare il log-in dobbiamo cercare solo i domicili con id relativo all'utente
	 */
	@RequestMapping(value="/ordine/{id}/settaDomicilio", method=RequestMethod.GET)
	public String settaModalitàDomicilio(Model model,@PathVariable("id")Long id)
	{
		
		Credentials c=getCliente();
		
		Cliente cliente=c.getUser();
		
		Ordine ordine=this.ordineService.trovaPerId(id);
		ordine.setTipo("Domicilio");
		model.addAttribute("ordine",ordine);
		model.addAttribute("domicili",this.domService.domiciliPerUtente(cliente));	//prendiamo i domicili del solo cliente loggato
		model.addAttribute("domicilio", new Domicilio());
		logger.debug("TOTALE ORDINE = "+ ordine.getTotale());
		logger.debug("L'ORDINE NUMERO " + ordine.getId()+ "è STATO SETTATO A " + ordine.getTipo());
		this.ordineService.inserisci(ordine);
		

		return "ordine/selezionaDomicilio.html";
	}
	/*
	 * Selezione della modalità di consegna ad asporto
	 */
	@RequestMapping(value="/ordine/{id}/settaAsporto", method=RequestMethod.GET)
	public String settaModalitàAsporto(Model model, @PathVariable("id")Long id)
	{
		Ordine ordine=this.ordineService.trovaPerId(id);
		ordine.setTipo("Asporto");
		model.addAttribute("ordine",ordine);
		this.ordineService.inserisci(ordine);
		logger.debug("L'ORDINE NUMERO " + ordine.getId()+ "è STATO SETTATO A  asporto");
		Credentials c=getCliente();
		Cliente cliente=c.getUser();
		model.addAttribute("cliente",cliente);	
		return "ordine/infoFatturazione.html";
	}
	/*
	 * L'utetnte seleziona un domicilio precedentemente inserito 
	 * Nota! @Mattia dovrà definre la gestione del 'nuovo domicilio'
	 * GESTIONE DI INPUT DA RADIOBUTTON
	 */
	@RequestMapping(value="/ordine/{id}/settaDomicilio", method=RequestMethod.POST)
	public String settaDomicilioDiConsegna (Model model, @ModelAttribute("domicilio")Domicilio dom, @PathVariable("id")Long id)
	{
		
		Domicilio domicilio=this.domService.domicilioPerId(dom.getId());
		logger.debug("E' STATO SELEZIONATO IL DOMICILIO : "+ domicilio.getIndirizzo());
		Ordine or=this.ordineService.trovaPerId(id);
		or.setIndirizzoConsegna(dom);
		logger.debug("TOTALE ORDINE = "+ or.getTotale());
		
		this.ordineService.inserisci(or);
		model.addAttribute("ordine",or);
		model.addAttribute("domicilio",domicilio);
		Credentials c=getCliente();
		
		Cliente cliente=c.getUser();
		model.addAttribute("cliente",cliente);	
		return "ordine/infoFatturazione.html";
	}
	@RequestMapping(value="/ordine/{id}/indietro", method=RequestMethod.GET)
	public String goBak(Model model, @PathVariable("id")Long id)
	{
		
		Ordine or=this.ordineService.trovaPerId(id);
		List<Prodotto> listaProdotti=this.prodService.tutti();
		model.addAttribute("prodotti",listaProdotti);
		model.addAttribute("ordine",or);
		
		return "ordine/selezionaProdotto.html";
	}

	@RequestMapping(value="/ordine/{id}/infoFatt", method=RequestMethod.GET)
	public String infoFatturazione(Model model, @PathVariable("id")Long id)
	{
		//in questa sezione vi si accede sia per la creazione che per la modifica dell'ordine
		//se l'orario è gia presente, allora è un ordine in modifica, altrimenti è un nuovo ordine
		
		Ordine ordine = this.ordineService.trovaPerId(id);
		if(ordine.getOrarioConsegna()==null)	//nuovo ordine
		{	
			model.addAttribute("ordine",new Ordine());	//uso un ordine fittizio per acquisite le info
		}
		else
		{   
			
						model.addAttribute("ordine",ordine);
		}
		Credentials c=getCliente();
		Cliente cliente=c.getUser();
		Domicilio dom=this.domService.domicilioPerId(ordine.getIndirizzoConsegna().getId());
		model.addAttribute("domicilio",dom);
		model.addAttribute("cliente",cliente);
		
		return "ordine/infoFatturazione.html";
	}
	
	
	@RequestMapping(value="/ordine/{id}/infoFatt", method=RequestMethod.POST)
	public String infoFatturazione(Model model,@PathVariable("id")Long id, @ModelAttribute("ordine")Ordine or,
			BindingResult bindingResult)
	{
		
				
		Credentials c =getCliente();
		Cliente cliente = c.getUser();
		
		//diversi settaggi
		Ordine ordine=this.ordineService.trovaPerId(id);
		
		this.ordineValidator.validate(or,bindingResult);
		if(!bindingResult.hasErrors())
		{
			ordine.setInfoFatturazione(or.getOrarioConsegna(), or.getCommento(), or.getTipologiaDiConsegna());
			
			
			logger.debug("L'UTETNE LOGGATO HA ID : " +  cliente.getId());
			
			logger.debug("SI sta per confermare l'ordine"+ ordine.getId());
			logger.debug("Modalitò di consegna: " + ordine.getTipo());
			logger.debug("TIPO CONSEGNA: "+ ordine.getTipologiaDiConsegna());
			logger.debug("ORARIO: "+ ordine.getOrarioConsegna());
			logger.debug("COMMENTO: "+ ordine.getCommento());
			logger.debug("TOTALE ORDINE = "+ ordine.getTotale());
			
			
			ordine.setStato("in corso");
			
			
			List<LineaOrdine> lio=this.linea.prendiLineeOrdinePerOrdine( or);
			//se l'ordine è d'asporto, non c'è necessita di inserire il domicilio
			if(ordine.getTipo().equals("Domicilio"))
			{
				Domicilio dom=this.domService.domicilioPerId(ordine.getIndirizzoConsegna().getId());
				model.addAttribute("domicilio",dom);
			}
				
				model.addAttribute("ordine", ordine);
				model.addAttribute("lineeOrdine",lio);
				this.ordineService.inserisci(ordine);
			
			return "ordine/ricapitoloOrdine.html";
		}
		
		Domicilio dom=this.domService.domicilioPerId(ordine.getIndirizzoConsegna().getId());
		model.addAttribute("domicilio",dom);
		model.addAttribute("cliente",cliente);
		
		
		return "ordine/infoFatturazione.html";
		
	}
	
//===================================FINE CASO D'USO EFFETTUA ORDINE==============================================	

	
	/*
	 * Caso d'uso modifica ordine
	 */
	/*
	 * Un utente registrato può accedere ai suoi ordini e modificare gli ordini ancora in fase di esecuzione
	 * Puo modificare le info di fatturazione precedentemente inserite
	 * Si necessita la suddivisione degli ordini in : In corso [modificabili]
	 * 												  Terminati [non modificabili]
	 */
	@RequestMapping(value="/gestisciOrdini", method=RequestMethod.GET)
	public String storicoOrdini (Model model)
	{
		Credentials c=getCliente();		//prendo l'utente di sessione
		Cliente cliente=c.getUser();
		
		List<Ordine> listaOrdiniClienteInCorso=this.ordineService.prendiOrdiniPerClienteEStato(cliente, "in corso");	//in corso
		List<Ordine> listaOrdiniClienteTerminati=this.ordineService.prendiOrdiniPerClienteEStato(cliente, "terminato");
		model.addAttribute("cliente",cliente);
		model.addAttribute("ordiniInCorso", listaOrdiniClienteInCorso); //ordini in corso [modificabili]
		model.addAttribute("ordiniTerminati", listaOrdiniClienteTerminati);
		return "ordine/gestioneOrdini.html";
		
	}
	/*
	 * Per quanto riguarda gli ordini non modificabili, il cliente può visualizzarli, 
	 * *possibilità di rirordinarli*
	 */
	
	@RequestMapping(value="/visualizzaOrdine/{id}", method=RequestMethod.GET)
	public String viewOrdine(Model model, @PathVariable("id")Long id)
	{
		Ordine ordine=this.ordineService.trovaPerId(id);
		model.addAttribute("ordine",ordine);
		List<LineaOrdine> lio=this.linea.prendiLineeOrdinePerOrdine( ordine);
		//se l'ordine è d'asporto, non c'è necessita di inserire il domicilio
		if(ordine.getTipo().equals("Domicilio"))
		{
			Domicilio dom=this.domService.domicilioPerId(ordine.getIndirizzoConsegna().getId());
			model.addAttribute("domicilio",dom);
		}
			model.addAttribute("ordine", ordine);
			model.addAttribute("lineeOrdine",lio);
			
		
		return "ordine/ordine.html";
	}
	/*
	 * L'utetnte seleziona un ordine in corso per poterne modificare le info di fatturazione
	 * (se possibile anche le modalità di consegna e domicilio di consegna)
	 * oppure per eliminarlo
	 */
	@RequestMapping(value="/gestisciOrdine/{id}",method=RequestMethod.GET)
	public String editOrdine(Model model, @PathVariable("id")Long id)
	{
		Ordine ordine=this.ordineService.trovaPerId(id);
		model.addAttribute("ordine",ordine);
		List<LineaOrdine> lio=this.linea.prendiLineeOrdinePerOrdine( ordine);
		//se l'ordine è d'asporto, non c'è necessita di inserire il domicilio
		if(ordine.getTipo().equals("Domicilio"))
		{
			Domicilio dom=this.domService.domicilioPerId(ordine.getIndirizzoConsegna().getId());
			model.addAttribute("domicilio",dom);
		}
			model.addAttribute("ordine", ordine);
			model.addAttribute("lineeOrdine",lio);
			Credentials c=getCliente();
			Cliente cliente=c.getUser();
			
			model.addAttribute("cliente",cliente);
		return "ordine/gestisciOrdine.html";
		
	}
	
	/*
	 * Eliminazione di un ordine
	 */
	@RequestMapping(value="/ordine/{id}/elimina", method=RequestMethod.GET)
	public String eliminaOrdine(Model model, @PathVariable("id")Long id)
	{
		Ordine eliminare= this.ordineService.trovaPerId(id);
		
		this.ordineService.elimina(eliminare);
		return "home.html";
	}
	






}
