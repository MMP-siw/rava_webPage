package it.uniroma3.siw.spring.rava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.spring.rava.service.ProdottoService;

@Controller
public class mainController 
{
	@Autowired
	private ProdottoService prodottoService;
	
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


}
