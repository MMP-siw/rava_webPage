package it.uniroma3.siw.spring.rava.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.spring.rava.model.Domicilio;

@Controller
public class DomicilioController {
       @RequestMapping(value="/aggiungiDomicilio", method=RequestMethod.POST)
       public String aggiungiDomicilio(@ModelAttribute("domicilio") Domicilio domicilio) {
    	  return "";
       }
}
