package it.uniroma3.siw.spring.model;

import java.util.List;
import javax.persistence.ManyToMany;
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
