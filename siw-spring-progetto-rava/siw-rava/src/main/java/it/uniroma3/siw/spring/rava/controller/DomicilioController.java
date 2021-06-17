package it.uniroma3.siw.spring.rava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	
       @RequestMapping(value="/aggiungiDomicilio", method=RequestMethod.GET)
       public String aggiungiDomicilio(Model model) {
    	   Domicilio domicilio= new Domicilio();
    	   model.addAttribute("domicilio", domicilio);
    	  return "formDomicilio.html";
       }
       @RequestMapping(value="/aggiungiDomicilio", method=RequestMethod.POST)
       public String aggiungiDomicilio(@ModelAttribute("domicilio") Domicilio domicilio, Model model) {
          
    	   Credentials c= getCliente();
    	   Cliente cliente= c.getUser();
    	   cliente.addDomicilio(domicilio);
    	   domicilio.setUtente(cliente);
    	  domicilioService.inserisci(domicilio);
    	  clientService.saveCliente(cliente);
    	  model.addAttribute("domicili", domicilioService.domiciliPerUtente(cliente));
    	  return "ordine/selezionaDomicilio.html";
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
   	
}
