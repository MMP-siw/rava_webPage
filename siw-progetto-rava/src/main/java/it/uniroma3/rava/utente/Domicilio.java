package it.uniroma3.rava.utente;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @AllArgsConstructor @EqualsAndHashCode @ToString
@Entity
@NamedQuery(name="getAllDomicili",query="SELECT d FROM domicili d")
@Table(name = "domicili")
public class Domicilio {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@Column
	private String tipo; //per tipo si intende piazza, via, vicolo ecc...
	@Column
	private String indirizzo;
	@Column
	private int civico;
	@Column
	private int cap;
	
}
