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

import it.uniroma3.rava.menu.Prodotto;
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
	
	@Column
	private Date data;
	@Column
	private float totale;
	@Column
	private String commento;
	@Column
	private String tipo; //se asporto o domicilio
	@Column
	private Time orarioConsegna;
	@Column(nullable = false)
	private Time orarioPrevisto;
	@OneToOne
	private Domicilio indirizzoConsegna; //deve essere selezionato dal domicilio dell'utente
	   									 //però l'utente ha una lista di domicilii, come ci si arriva?
	
	@OneToOne
	private Utente destinatario;
	
	@OneToMany(mappedBy="ordini")
	private List<LineaOrdine> lineeOrdine;
	
	/*
	 * vedi operazione @SelezionaProdotto
	 * 
	 * l'oggetto ordine crea la linea d'ordine
	 * setta il prodotto e il quantitativo
	 * calcola il totale
	 * aggiunge la linea d'ordine nella sua lista
	 */
	public void creaLineaOrdine(Prodotto p, int qnt)
	{
		LineaOrdine lio= new LineaOrdine();	//creazione della linea di prodotto
		lio.setProdotto(p);					//settaggio del prodotto associato alla linea d'ordine
		lio.setQuantita(qnt);				//settaggio del quantitativo selezionato
		lio.setSubTotale(lio.getProdotto().getPrezzo()*qnt);	//settaggio del subtotale della linea(prezzoprodotto*qnt)
		this.setTotale(totale+lio.getSubTotale());	//settaggio del totale dell'ordine
		this.lineeOrdine.add(lio);
		
	}
	
	
	
}
