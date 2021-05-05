package it.uniroma3.rava.utente;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import it.uniroma3.rava.ordine.Ordine;
import it.uniroma3.rava.prenotazione.Prenotazione;
import lombok.*;

@Getter @Setter @AllArgsConstructor @EqualsAndHashCode @ToString
@Entity
@Table(name = "utente")
public class Utente {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@Column(nullable = false)
	private String nome;
	@Column(nullable = false)
	private String cognome;
	@Column(nullable = false)
	private String email;
	@Column(nullable = false)
	private String psw;
	@Column(nullable = false)
	private String telefono;
	
	@OneToMany(mappedBy = "utente")
	private List<Domicilio> domicilio;	//l'utente può definire una collezione di domicili (stile Amazon)
	
	@OneToMany(mappedBy = "utente")
	private List<Prenotazione> prenotazioni;	//l'utente conosce tutte le sue prenotazioni
	
	/*l'utente deve conoscere tutta la lista dei suoi ordini?*/
	@OneToMany(mappedBy="utente")
	private List<Ordine> ordiniEffettuati;


}
