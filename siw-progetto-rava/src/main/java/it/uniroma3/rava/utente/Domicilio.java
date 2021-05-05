package it.uniroma3.rava.utente;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @AllArgsConstructor @EqualsAndHashCode @ToString
@Entity
@Table(name = "domicili")
public class Domicilio {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@Column(nullable = false)
	private String tipo; //per tipo si intende piazza, via, vicolo ecc...
	@Column(nullable = false)
	private String indirizzo;
	@Column(nullable = false)
	private int civico;
	@Column(nullable = false)
	private int cap;
	

}
