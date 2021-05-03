package it.uniroma3.rava.ordine;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import it.uniroma3.rava.menu.Prodotto;
import it.uniroma3.rava.menu.Special;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @AllArgsConstructor @EqualsAndHashCode @ToString
@Entity
@Table(name = "lineeOrdine")
public class LineaOrdine {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@Column(nullable = false)
	private int quantita;
	
	/*la linea d'ordine può avere o solo uno o solo l'altro, come si blocca la cosa?*/
	@OneToOne
	private Prodotto prodotto;
	
	@OneToOne
	private Special special;

}
