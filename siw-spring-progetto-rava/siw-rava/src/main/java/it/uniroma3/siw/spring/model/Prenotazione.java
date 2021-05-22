package it.uniroma3.siw.spring.model;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @AllArgsConstructor @EqualsAndHashCode @ToString
@Entity
@Table(name = "prenotazioni")
public class Prenotazione {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@Column(nullable = false)
	private Date data;
	@Column(nullable = false)
	private Time orario;
	@Column(nullable = false)
	private int numeroPersone;
	@Column(nullable = false)
	private String commento;
	
	@OneToOne
	@JoinColumn(name = "utenti_id")
	private Cliente prenotante;
	
	@OneToOne
	@JoinColumn(name = "slotcalendario_id")
	private Slot slot;

}
