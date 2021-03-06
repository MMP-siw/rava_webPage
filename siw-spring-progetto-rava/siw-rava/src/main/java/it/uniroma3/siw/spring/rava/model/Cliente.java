package it.uniroma3.siw.spring.rava.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.*;

@Getter @Setter @AllArgsConstructor @EqualsAndHashCode @ToString @NoArgsConstructor
@Entity
@Table(name = "cliente")
public class Cliente {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@Column
	private String nome;
	@Column
	private String cognome;
	@Column
	private String email;
	@Column
	private String telefono;
	
	@OneToMany(mappedBy = "utente")
	private List<Domicilio> domicilio;	//l'utente può definire una collezione di domicili (stile Amazon)
	
	@OneToMany(mappedBy = "cliente")
	private List<Prenotazione> prenotazioni;	//l'utente conosce tutte le sue prenotazioni
	
	/*l'utente deve conoscere tutta la lista dei suoi ordini?*/
	@OneToMany(mappedBy="utente")
	private List<Ordine> ordiniEffettuati;
	
	/*
	 * Questo campo transiente memorizza l'ordine corrente su cui sta lavorando il cliente.
	 * Un cliente può creare un solo ordine alla volta
	 * 
	 */
	@OneToOne
	private Ordine ordineCorrente;
	
	/*
	 * Aggiunta di un ordine nella lista degli ordini
	 */
	public void addOrdine(Ordine o)
	{
		this.ordiniEffettuati.add(o);
	}
	
	public void addDomicilio(Domicilio domicilio) {
		this.domicilio.add(domicilio);
	}
	
	public void removeDomicilio(Domicilio domicilio) {
		this.domicilio.remove(domicilio);
	}
	
	/*
	 * Resitutuzione di un domiciolio specifico-->necessario per l'operazione di sistema
	 *@selezionaDomiciolio
	 */

}
