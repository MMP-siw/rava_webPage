package it.uniroma3.siw.spring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.*;

@Getter @Setter @AllArgsConstructor @EqualsAndHashCode @ToString
@Entity
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
