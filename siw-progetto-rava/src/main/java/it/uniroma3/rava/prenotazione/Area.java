package it.uniroma3.rava.prenotazione;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import it.uniroma3.calendario.SlotCalendario;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @AllArgsConstructor @EqualsAndHashCode @ToString
@Entity
@Table(name = "aree")
public class Area {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@Column(nullable = false) 
	private String nome;
	@Column(nullable = false)
	private int personeMax;
	
	@OneToMany(mappedBy = "aree")
	private List<Prenotazione> prenotazioni;	//ogni area ha una lista di prenotazioni da tener traccia
	
	@OneToMany(mappedBy="area")
	private List<SlotCalendario> slots;		//ogni area ha N slot che caratterizzano gli orari di prenotazione

}
