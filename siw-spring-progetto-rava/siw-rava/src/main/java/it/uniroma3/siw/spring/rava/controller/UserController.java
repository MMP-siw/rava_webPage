package it.uniroma3.siw.spring.rava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import it.uniroma3.siw.spring.rava.model.Cliente;
import it.uniroma3.siw.spring.rava.model.Credentials;
import it.uniroma3.siw.spring.rava.service.CredentialsService;

@Controller
public class UserController {
	
	@Autowired
	CredentialsService credentialsService;
	
	@RequestMapping(value= {"/utente"}, method=RequestMethod.GET)
	public String index(Model model)
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
		
		model.addAttribute("utente", cliente);
		return "authentication/user";
	}

}
