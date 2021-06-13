package it.uniroma3.siw.spring.rava.model;
//package it.uniroma3.siw.model.test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import java.sql.Time;
//import java.time.LocalTime;
//
//import javax.persistence.Persistence;
//
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import it.uniroma3.siw.spring.rava.model.Cliente;
//import it.uniroma3.siw.spring.rava.model.Domicilio;
//import it.uniroma3.siw.spring.rava.model.Ordine;
//import it.uniroma3.siw.spring.rava.model.Prodotto;
//import it.uniroma3.siw.spring.rava.service.ClienteService;
//import it.uniroma3.siw.spring.rava.service.DomicilioService;
//import it.uniroma3.siw.spring.rava.service.LineaOrdineService;
//import it.uniroma3.siw.spring.rava.service.OrdineService;
//import it.uniroma3.siw.spring.rava.service.ProdottoService;
//
//
///*
// * Il floe di un ordine a domicilio è il seguente:
// * -1)Creazione dell'ordine
// * -2*)Selezione del prodotto e creazione della linea d'ordine
// * -3)Selezione della modalità di consegna
// * -4)Selezione del domicilio
// * -5)Inserimento delle info di fatturazione
// * -6)Conferma ordine 
// */
//class OrdineADomicilioTest 
//{
//	
//	
//	private static Ordine ordine;
//	private  static Domicilio dom;
//	private  static Cliente cliente;
//	private  static Prodotto p1;
//	private  static Prodotto p2;
//	
//	@Autowired 
//	private static ClienteService cs;
//	
//	
//	@Autowired
//	private static OrdineService oS;
//	
//	@Autowired
//	private static ProdottoService pS;
//	
//	@Autowired
//	private static LineaOrdineService loS;
//	
//	@Autowired
//	private static DomicilioService dS;
//	
//	@BeforeAll
//	public static void setUpOrdine()
//	{
//		ordine= new Ordine();			//corrisponde alla creazione dell'ordine all'operazione @IniziaNuovoOrdine
//		
//		/*
//		 * Creo gli oggetti che saranno già presenti nel db al momenti della creazione dell'ordine
//		 * Siamo al punto 0
//		 */
//		p1=new Prodotto();									//creazione del prodotto
//		p1.setDescrizione("hamburger con insalata etc...");
//		p1.setNome("Hamburger");
//		p1.setPrezzo(10f);
//		
//		p2=new Prodotto();									//creazione del prodotto
//		p2.setDescrizione("hot dog con crauti");
//		p2.setNome("wustel");
//		p2.setPrezzo(5f);
//		
//		dom= new Domicilio();								//creazione del domicilio
//		dom.setCap(100);
//		dom.setCivico(2);
//		dom.setIndirizzo("via bbho");
//		dom.setTipo("viale");
//		
//		dS.inserisci(dom);								//persistenza degli oggetti
//		pS.inserisci(p1);
//		pS.inserisci(p2);
//	}
//	/*
//	 * A questo punto del caso d'uso il client inserisce nell'ordine un prodotto.
//	 * Bisogna--->cercare il rpodotto per Id
//	 * 		  --->creare la linead'ordine
//	 * 
//	 * Il cliente seleziona  il prodotto id1 con quantità 5
//	 * 
//	 * Verifico che: l'ordine è stato inserito con successo e il totale dell'ordine è corretto
//	 */
//	@Test
//	public void selezionaProdotto()
//	{
//		Prodotto p=this.pS.prodottoPerId(p1.getId());
//		
//		this.ordine.creaLineaOrdine(p, 2);		//questa operazione viene effettuata dal controller Ordine
//												//prende l'ordine corrente passato dal model e invoca il metodo
//												//creaLineaOrdine
//		
//		assertEquals(20f, this.ordine.getTotale());
//	}
//	/*
//	 * A questo punto del caso d'uso, il client seleziona il tipo di modalita di consegna
//	 * Si invoca il metodo setTipo relativo all'ordine che è in corso
//	 * Tale operazione viene effettuata nel controller MVC Ordine.
//	 *  IL controller riceve la stringa che definisce il tipo di consegna dal front controller e lo setta 
//	 *  
//	 *  In tymeleaf, il client preme il botton "A Domicilio"
//	 *  Si elabora il value del bottone
//	 *  Si associa esso all'ordine corrente
//	 */
//	@Test
//	public void setModalitàDiConsegna()
//	{
//		this.ordine.setTipo("Domicilio");
//	}
//	
//	/*
//	 * Selezione del domicilio di consegna
//	 * Il cliente, una volta selezionato la modalità di consegna Domicilio, deve selezionare il domicilio di consegna
//	 * Devono essere: mostrati tutti i domicili del cliente loggato *
//	 * 				  selezionare da essi il domicilio per id
//	 * 				  associare il domicilio selezionato all'ordine corrente
//	 */
//	@Test
//	public void selezionaDomicilio()
//	{
//		Domicilio d=this.dS.domicilioPerId(this.dom.getId());		//operazione effettuata nel controller MVC Ordine
//	}
//	
//	/*
//	 * In questo passo del caso d'uso, vediamo che il cliente inserisce le info di fatturazione
//	 * Nome, cognome e domicilio sono già stati settati nei passi precedenti.
//	 * Il cliente inserisce un : orario di consegna (orario definito o Prima possibile)
//	 * 							 un commento relativo all'ordine
//	 */							 
//	@Test
//	public void inserisciInfoFatturazione()
//	{
//		this.ordine.setInfoFatturazione(LocalTime.now(), "Commento");		//ancora da implementare
//	}
//	
//	/*
//	 * Conferma dell'ordine
//	 * In questo passo del caso d'uso il cliente conferma la sua volota di procedere all'ordine.
//	 * L'ordine: Setta che il cliente corrente associato è il cliente loggato
//	 * 			 divente persistente.
//	 * Poichè in modalità cascade, anche le linee di ordine vengono memorizzate
//	 * Necessitiamo: un metodo per settare il cliente (conferma Ordine)
//	 * 				un meotodo per inserire l'ordine nella lista delgi ordini del cliente corrente
//	 * 				 un metodo per rendere persistente l'ordine
//	 * 				
//	 */
//	@Test
//	public void confermaOrdine()
//	{
//		this.ordine.confermaOrdine(cliente);
//		this.oS.inserisci(ordine);
//		this.cs.inserisci(cliente);
//		
//		
//	}
//	
//	
//	
//	
//	
//	
//	
//
//}
