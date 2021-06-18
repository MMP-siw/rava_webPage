package it.uniroma3.siw.spring.rava.controller;


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

import it.uniroma3.siw.spring.rava.controller.validator.DomicilioValidator;
import it.uniroma3.siw.spring.rava.model.Cliente;
import it.uniroma3.siw.spring.rava.model.Credentials;
import it.uniroma3.siw.spring.rava.model.Domicilio;
import it.uniroma3.siw.spring.rava.service.ClienteService;
import it.uniroma3.siw.spring.rava.service.CredentialsService;
import it.uniroma3.siw.spring.rava.service.DomicilioService;

@Controller
public class DomicilioController {
	
	@Autowired
	private DomicilioService domicilioService;
	@Autowired
	private CredentialsService credentialService;
	@Autowired
	private ClienteService clientService;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private DomicilioValidator domicilioValidator;
	
       @RequestMapping(value="/aggiungiDomicilio", method=RequestMethod.GET)
       public String aggiungiDomicilio(Model model) {
    	   Domicilio domicilio= new Domicilio();
    	   model.addAttribute("domicilio", domicilio);
    	  return "formDomicilio.html";
       }
       @RequestMapping(value="/aggiungiDomicilio", method=RequestMethod.POST)
       public String aggiungiDomicilio(@ModelAttribute("domicilio") Domicilio domicilio, BindingResult bindingResult, Model model) {
          
    	   Credentials c= getCliente();
    	   Cliente cliente= c.getUser();
    	   this.domicilioValidator.validate(domicilio, bindingResult);
    	   if (bindingResult.hasErrors()) {
    		   return "formDomicilio";
    	   }
    	   cliente.addDomicilio(domicilio);
    	   domicilio.setUtente(cliente);
    	  domicilioService.inserisci(domicilio);
    	  clientService.saveCliente(cliente);
    	  model.addAttribute("domicili", domicilioService.domiciliPerUtente(cliente));
    	  return "gestisciDomicili.html";
       }
       
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
   						
   				Credentials c = this.credentialService.getCredentials(username);
   				
   				return c;
   	}
   	
       
       
       //------------------------------------------------------------
       
       
       @RequestMapping(value="/gestisciDomicili", method=RequestMethod.GET)
       public String goGestisciDomicili(Model model) {
    	   Credentials c= getCliente();
    	   Cliente cliente= c.getUser();
    	   model.addAttribute("domicili", domicilioService.domiciliPerUtente(cliente));
    	   model.addAttribute("domicilio", new Domicilio());
    	   return "gestisciDomicili.html";
       }
       
       @RequestMapping(value="/eliminaDomicilio", method=RequestMethod.POST)
       public String deleteAdd(Model model, @ModelAttribute("domicilio") Domicilio dom) {
    	   Domicilio domicilio= this.domicilioService.domicilioPerId(dom.getId());
    	   logger.debug("è stato selezionato il domicilio di "+ domicilio.getIndirizzo());
    	   Credentials c=getCliente();
   		   Cliente cliente=c.getUser();
   		   cliente.removeDomicilio(domicilio);
   		   this.domicilioService.elimina(domicilio);
   		   model.addAttribute("domicili", this.domicilioService.domiciliPerUtente(cliente));
   		   return "gestisciDomicili";
       }
}
