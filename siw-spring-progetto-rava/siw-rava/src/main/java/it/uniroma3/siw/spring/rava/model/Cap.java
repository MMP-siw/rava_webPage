package it.uniroma3.siw.spring.rava.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

/*
 * Questa classe è un entità che permette di memorizzare i cap nel range del ristorante.
 * Ogni cap è caratterizzato da un valore e un costo relativo
 * (maggiore è la distanza, maggiore è il costo di consegna)
 * (idee future-->per ordini superiori a x non si hanno costi di consegna)
 * @Matteo 
 */
@Entity
@Data
public class Cap 
{
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column
	private String Valore;
	
	@Column
	private int costoConsegna;
	
	
}
