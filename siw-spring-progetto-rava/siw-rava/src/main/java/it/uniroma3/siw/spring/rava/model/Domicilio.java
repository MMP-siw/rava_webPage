package it.uniroma3.siw.spring.rava.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.*;

@Getter @Setter @AllArgsConstructor @EqualsAndHashCode @ToString
@Entity
@Data
@NoArgsConstructor
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
	@ManyToOne
	private Cliente  utente;
	
	@Override
	public String toString()
	{
		return (this.tipo + this.indirizzo + " (" +this.civico + " )");
	}

}
