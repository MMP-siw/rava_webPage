package it.uniroma3.siw.spring.rava.model;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @AllArgsConstructor @EqualsAndHashCode @ToString @NoArgsConstructor
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
	
	@ManyToOne
	@JoinColumn(name = "utenti_id")
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name = "slotcalendario_id")
	private Slot slot;

}
