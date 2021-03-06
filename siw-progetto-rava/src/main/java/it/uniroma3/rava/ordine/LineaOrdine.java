package it.uniroma3.rava.ordine;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import it.uniroma3.rava.menu.Prodotto;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @AllArgsConstructor @EqualsAndHashCode @ToString
@NoArgsConstructor
@Entity
@Table(name = "lineeOrdine")
public class LineaOrdine {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@Column(nullable = false)
	private int quantita;
	
	@OneToOne
	private Prodotto prodotto;
	
	@Column
	private float subTotale;

}
