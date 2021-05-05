package it.uniroma3.rava.ordine;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import it.uniroma3.rava.utente.Domicilio;
import it.uniroma3.rava.utente.Utente;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @AllArgsConstructor @EqualsAndHashCode @ToString
@Entity
@Table(name = "ordini")
public class Ordine {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@Column(nullable = false)
	private Date data;
	@Column(nullable = false)
	private float totale;
	@Column(nullable = false)
	private String commento;
	@Column(nullable = false)
	private String tipo; //se asporto o domicilio
	@Column(nullable = false)
	private Time orarioConsegna;
	@Column(nullable = false)
	private Time orarioPrevisto;
	private Domicilio indirizzoConsegna; //deve essere selezionato dal domicilio dell'utente
	   									 //però l'utente ha una lista di domicilii, come ci si arriva?
	
	@OneToOne
	private Utente destinatario;
	
	@OneToMany(mappedBy="ordini")
	private List<LineaOrdine> lineeOrdine;
	
}
