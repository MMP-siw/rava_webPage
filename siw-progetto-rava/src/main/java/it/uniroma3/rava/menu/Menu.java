package it.uniroma3.rava.menu;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @AllArgsConstructor @EqualsAndHashCode @ToString
public class Menu {
	
	/*
	 * se il menù è uno solo non ha bisogno di 
	 * essere memorizzato come entità
	 */

	@ManyToMany(mappedBy = "menu")
	private List<Prodotto> prodotti;

}
