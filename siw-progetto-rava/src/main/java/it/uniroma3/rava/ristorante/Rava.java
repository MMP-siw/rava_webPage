package it.uniroma3.rava.ristorante;

import java.util.List;

import it.uniroma3.calendario.Calendario;
import it.uniroma3.rava.prenotazione.Area;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @AllArgsConstructor @EqualsAndHashCode @ToString
public class Rava {
	
	private String nome;
	private String indirizzo;
	private List<Integer> capValidi;
	private Calendario calendario;
	private Area areaEsterno;
	private Area areaInterno;

}
