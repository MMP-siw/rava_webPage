package it.uniroma3.calendario;

import java.sql.Date;
import java.sql.Time;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import it.uniroma3.rava.prenotazione.Area;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @AllArgsConstructor @EqualsAndHashCode @ToString
@Entity
@Table(name = "slotsCalendario")
public class SlotCalendario {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@Column(nullable = false)
	private Date data;
	@Column(nullable = false)
	private Time orario;
	@Column(nullable = false)
	private int postiDisponibili;
	
	@ManyToOne
	@JoinColumn(name = "area_id")
	private Area area;					//ogni Slot è realtiva a 0 o 1 Area

}
