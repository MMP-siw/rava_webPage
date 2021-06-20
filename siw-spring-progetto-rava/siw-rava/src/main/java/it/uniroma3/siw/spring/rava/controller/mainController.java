package it.uniroma3.siw.spring.rava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.spring.rava.model.Cliente;
import it.uniroma3.siw.spring.rava.model.Credentials;
import it.uniroma3.siw.spring.rava.model.Ordine;
import it.uniroma3.siw.spring.rava.model.Prodotto;
import it.uniroma3.siw.spring.rava.service.CredentialsService;
import it.uniroma3.siw.spring.rava.service.ProdottoService;

@Controller
public class mainController 
{
	@Autowired
	private ProdottoService prodottoService;
	@Autowired
	private CredentialsService credentialsService;
	
	@RequestMapping(value= {"/", "home"}, method=RequestMethod.GET)
	public String index(Model model)
	{
		return "home.html";
	}
	
	@RequestMapping(value="/menu", method=RequestMethod.GET)
	public String goToMenuPage(Model model) {
		model.addAttribute("menu", this.prodottoService.tutti());
		return "menu.html";
	}
	
	@RequestMapping(value="/menu/prodotto/{id2}", method=RequestMethod.GET)
	public String goProduct(@PathVariable("id2")Long id2, Model model) {

	

		Prodotto prod = this.prodottoService.prodottoPerId(id2);

		model.addAttribute("prodotto", prod);
		return "prodottoMenu.html";
	}
	



}
