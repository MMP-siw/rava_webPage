package it.uniroma3.siw.spring.rava.model;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @EqualsAndHashCode @ToString @AllArgsConstructor @NoArgsConstructor
public class Rava {
	
	private String nome;
	private String indirizzo;
	public final static List<Integer> cap = List.of(197, 198, 199);
	private Menu menu;
	private List<Prenotazione> prenotazioniTotali;
	public final static int postiMax = 10;

}
