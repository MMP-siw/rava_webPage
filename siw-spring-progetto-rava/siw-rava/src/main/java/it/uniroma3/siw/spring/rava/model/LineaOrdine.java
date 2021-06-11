package it.uniroma3.siw.spring.rava.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.*;

@Getter @Setter @AllArgsConstructor 
@NoArgsConstructor
@Entity @Data
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
	
	@ManyToOne
	private Ordine ordine;
	
	@Override
	public String toString()
	{
		return (this.getProdotto().toString()+" "+ this.quantita +" "+ this.subTotale );
	}

}
