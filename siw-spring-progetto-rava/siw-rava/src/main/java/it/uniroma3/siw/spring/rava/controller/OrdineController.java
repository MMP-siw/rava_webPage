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






	/**
	 *Caso d'uso ordina (asporto o domiclio)
	 * 1)un utente vuole effettuare un ordine a domicilio
	 * 2)il cliente si logga al sistema
	 * 3)Il cliente seleziona l'operaizone Ordine
	 * ===========LOOP=========================
	 * 4)Il sistema mostra l'elenco di prodotti che è possibile acquistare
	 * 5)l'utetente seleziona un prodotto
	 * 6)Il sistema mostra una descrizione del prodotto
	 * 7)l'utetente lo inserisce nell'ordine
	 * =====================================
	 * 8)Il clinete termina la scelta
	 * 9)Il sistema mostra le modalità di consegna
	 * 10)il cliente seleziona modalità a Domicilio
	 * 11)Il sistema mostra l'elenco di domicilio a disposizione del cliente
	 * 12)Il cliente seleziona un domicilio
	 * 13)Il Cliente inserisce le info di fatturazione (orario e commento)
	 * 14)Il cliente conferma l'ordine
	 * 
	 * Estensione
	 * 	9a)Il cliente seleziona modalità da asprto
	 *     10)Il cliente inserisce le info di fatturazione
	 * 
	 */
	
	/*
	 * TO DO if needed
	 * Update 20/06/2021
	 * 
	 * Per distinguere i diversi ordini->Se un ordine è in corso di creazione allora è in uno stato di "in corso"
	 * 									 Se un ordine è stato piazzato e ancora non consegnato allora "in consegna"
	 * 									 Se un ordine è stato consegnato allora è in stato "terminato"
	 * SI introducono 3 costanti all'interno del model Ordina
	 * 
	 * Quando un cliente inizia un ordine-->setStatoInCorso()
	 * Quando un cliente conferma l'ordine-->setStatoInConsegna()
	 * Quando un cliente riceve l'ordine, l'admin lo setta a terminato-->setStatoInTerminato();
	 * 
	 * Utilità
	 * 	
	 * Se durante la navigazione, un utente modifica l'url, inserendo un numero differente da quello dato
	 * allora: si controlla che l'ordine è in stato "in corso" se lo è si controlla che l'utente corrispondente sia 
	 * lo stesso di quello loggato.
	 * */

	/*
	 * SOLUZIONE 2, senza gestione della sessione ma con l'id dell'ordine memorizzato nel url
	 */
	private  Credentials getCredentials() {
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
	private Cliente getCliente()
	{
		//retrieve current user
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;

		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		} else {
			username = principal.toString();
		}
				
		Credentials c = this.credentialsService.getCredentials(username);
		Cliente cliente = c.getUser();
		return cliente;
	}
	
	/*
	 * l'utente desidera effettuare un nuovo ordine
	 * Passo 1 ordina
	 */
	@RequestMapping(value="/ordinaProdotto", method=RequestMethod.GET)
	public String prendiTuttiIProdotti(Model model)
	{
		Credentials c=getCredentials();
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
		//cliente.addOrdine(ordineCorrente);
		
		//this.ordineService.inserisci(ordineCorrente);
		ordineCorrente.setCommento("");
		cliente.setOrdineCorrente(ordineCorrente);		//settimao che l'ordine corrente (transiente) è quello creato
		
		logger.debug("L'UTENTE HA CREATO L'ORDINE " + cliente.getOrdineCorrente().getCommento());
		model.addAttribute("ordine",ordineCorrente);
		model.addAttribute("prodotti",listaProdotti);
		this.ordineService.inserisci(ordineCorrente);

		this.clienteService.saveCliente(cliente);
		
		return "ordine/selezionaProdotto.html";
	}
	
	@RequestMapping(value="/ordine/annulla", method= RequestMethod.GET)
	public String annullaOrdine(Model model)
	{
		Cliente c=getCliente();
		Ordine ordine=c.getOrdineCorrente();
		c.setOrdineCorrente(null);
		this.clienteService.saveCliente(c);
		this.ordineService.elimina(ordine);
		return "home.html";
	}
	
	
	/*
	 * L'utente Seleziona il prodotto desiderato
	 * Passo 2 caso d'uso Ordina
	 */
	@RequestMapping(value = "/ordine/prodotto/{id2}", method = RequestMethod.GET)
	public String getProdotto(@PathVariable("id2")Long id2, Model model) {

		Cliente c=getCliente();		//prendo il cliente corrente loggato
		Ordine ordine=c.getOrdineCorrente(); // prendo l'0rdine corrente non salvato nel db
		logger.debug("STAI LAVORANDO CON L'ORDINE NUMERO:"+ ordine.getCommento());
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
	@RequestMapping(value="/ordine/prodotto/{id2}/inserisci", method=RequestMethod.GET)
	public String addProdottoInOrdine(Model model, @PathVariable("id2")Long id2 )
	{

		Cliente c= getCliente();
		
		Ordine ordine=c.getOrdineCorrente();
		
		logger.debug("L'ORDINE DEL CLIENTE E' : " + c.getOrdineCorrente().getCommento());
		Prodotto p=this.prodService.prodottoPerId(id2);

		LineaOrdine lio= new LineaOrdine();
		lio.setProdotto(p);
		lio.setSubTotale(p.getPrezzo());
		lio.setQuantita(1);		//creo la linea d'ordine e inserisco il prodotto
		
		ordine.setTotale(ordine.getTotale() + lio.getSubTotale());
		ordine.aggoungiLineaOrdine(lio);
		lio.setOrdine(ordine);
		
		//anche le linee ordine non vengono memorizzate finche non viene memorizzato l'ordine
		
		
		logger.debug("TOTALE DELL'ORDINE è: " + ordine.getTotale());
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
	@RequestMapping(value="/ordine/settaDomicilio", method=RequestMethod.GET)
	public String settaModalitàDomicilio(Model model)
	{
		
		
		
		Cliente cliente=getCliente();
		
		
		Ordine ordine=cliente.getOrdineCorrente();
		
		ordine.setTipo("Domicilio");
		model.addAttribute("ordine",ordine);
		model.addAttribute("domicili",this.domService.domiciliPerUtente(cliente));	//prendiamo i domicili del solo cliente loggato
		model.addAttribute("domicilio", new Domicilio());
		logger.debug("TOTALE ORDINE = "+ ordine.getTotale());
		logger.debug("L'ORDINE NUMERO "+ "è STATO SETTATO A " + ordine.getTipo());
		

		return "ordine/selezionaDomicilio.html";
	}
	

	/*
	 * Selezione della modalità di consegna ad asporto
	 */
	@RequestMapping(value="/ordine/settaAsporto", method=RequestMethod.GET)
	public String settaModalitàAsporto(Model model)
	{
		Cliente cliente=getCliente();
		
		Ordine ordine=cliente.getOrdineCorrente();
		ordine.setTipo("Asporto");
		model.addAttribute("ordine",ordine);
		this.ordineService.inserisci(ordine);
		logger.debug("TOTALE ORDINE: "+ ordine.getTotale());
		logger.debug("L'ORDINE NUMERO " + ordine.getId()+ "è STATO SETTATO A  asporto");
		
		model.addAttribute("cliente",cliente);	
		
		return "ordine/infoFatturazione.html";
	}
	/*
	 * L'utetnte seleziona un domicilio precedentemente inserito 
	 * Nota! @Mattia dovrà definre la gestione del 'nuovo domicilio'
	 * GESTIONE DI INPUT DA RADIOBUTTON
	 */
	@RequestMapping(value="/ordine/settaDomicilio", method=RequestMethod.POST)
	public String settaDomicilioDiConsegna (Model model, @ModelAttribute("domicilio")Domicilio dom)
	{
		
		Domicilio domicilio=this.domService.domicilioPerId(dom.getId());
		logger.debug("E' STATO SELEZIONATO IL DOMICILIO : "+ domicilio.getIndirizzo());
		Cliente cliente=getCliente();
		Ordine or=cliente.getOrdineCorrente();
		or.setIndirizzoConsegna(dom);
		logger.debug("TOTALE ORDINE = "+ or.getTotale());
		
		this.ordineService.inserisci(or);
		model.addAttribute("ordine",or);
		model.addAttribute("domicilio",domicilio);
		
		
		
		model.addAttribute("cliente",cliente);	
		return "ordine/infoFatturazione.html";
	}
	@RequestMapping(value="/ordine/indietro", method=RequestMethod.GET)
	public String goBak(Model model)
	{
		Cliente cliente=getCliente();
		Ordine or=cliente.getOrdineCorrente();
		List<Prodotto> listaProdotti=this.prodService.tutti();
		model.addAttribute("prodotti",listaProdotti);
		model.addAttribute("ordine",or);
		
		return "ordine/selezionaProdotto.html";
	}

	@RequestMapping(value="/ordine/infoFatt", method=RequestMethod.GET)
	public String infoFatturazione(Model model)
	{
		//in questa sezione vi si accede sia per la creazione che per la modifica dell'ordine
		//se l'orario è gia presente, allora è un ordine in modifica, altrimenti è un nuovo ordine
		
		Cliente c=getCliente();
		Ordine ordine=c.getOrdineCorrente();
		
		if(ordine.getOrarioConsegna()==null)	//nuovo ordine
		{	
			model.addAttribute("ordine",new Ordine());	//uso un ordine fittizio per acquisite le info
		}
		else
		{   
			
			model.addAttribute("ordine",ordine);
		}
		
		if (ordine.getTipo().equals("Domicilio")) {
			Domicilio dom=this.domService.domicilioPerId(ordine.getIndirizzoConsegna().getId());
			model.addAttribute("domicilio",dom);
		}
		model.addAttribute("cliente",c);
		
		return "ordine/infoFatturazione.html";
	}
	
	
	@RequestMapping(value="/ordine/infoFatt", method=RequestMethod.POST)
	public String infoFatturazione(@ModelAttribute("ordine")Ordine or, BindingResult bindingResult, 
			Model model)
	{
		
				
		Credentials c =getCredentials();
		Cliente cliente = c.getUser();
		
		//diversi settaggi
		Ordine ordine=cliente.getOrdineCorrente();
		
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
			
			List<LineaOrdine> lio=ordine.getLineeOrdine();
			//se l'ordine è d'asporto, non c'è necessita di inserire il domicilio
			if(ordine.getTipo().equals("Domicilio"))
			{
				Domicilio dom=this.domService.domicilioPerId(ordine.getIndirizzoConsegna().getId());
				model.addAttribute("domicilio",dom);
			}
				
				model.addAttribute("ordine", ordine);
				model.addAttribute("lineeOrdine",lio);
				
				this.ordineService.inserisci(ordine);
				cliente.setOrdineCorrente(null);
				
			
			return "ordine/ricapitoloOrdine.html";
		}
		
		model.addAttribute("cliente",cliente);
		/**cosa da provare un attimo**/
		if (ordine.getTipo().equals("Domicilio")) {
			Domicilio dom=this.domService.domicilioPerId(ordine.getIndirizzoConsegna().getId());
			model.addAttribute("domicilio",dom);
		}
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
		Credentials c=getCredentials();		//prendo l'utente di sessione
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
		Cliente cliente=getCliente();
				cliente.setOrdineCorrente(ordine);
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
		Cliente cliente=getCliente();
		cliente.setOrdineCorrente(ordine);
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
			Credentials c=getCredentials();
			
			
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
		Cliente cliente=getCliente();
		cliente.setOrdineCorrente(null);
		
		this.ordineService.elimina(eliminare);
		return "home.html";
	}
	
	




}
