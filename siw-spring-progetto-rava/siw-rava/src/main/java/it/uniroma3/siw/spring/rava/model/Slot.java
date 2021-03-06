package it.uniroma3.siw.spring.rava.model;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @AllArgsConstructor @EqualsAndHashCode @ToString
@Entity
@Table(name = "slotsCalendario")
public class Slot {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	/**
	@Column(nullable = false)
	private Date data;
	@Column(nullable = false)
	private Time orario;
	**/
	
	@Column(nullable=false)
	private LocalDate data;
	
	@Column(nullable = false)
	private FasciaOraria fasciaOraria;
	
	@Column(nullable = false)
	private int postiDisponibili;
	
	/**
	@OneToMany(mappedBy = "slot")
	private List<Prenotazione> prenotazioni;
	
	public int contaPostiDisponibili() {
		int app = 0;
		for (Prenotazione p: prenotazioni) {
			app += p.getNumeroPersone();
		}
		return this.postiDisponibili - app;
	}**/

}
