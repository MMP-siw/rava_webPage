package it.uniroma3.siw.spring.model;

import java.sql.Date; 
import java.sql.Time;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
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
	private String stato;	//definisce lo stato in cui si trova l'ordine, una volta creato è in stato di 
							//preparazione, una volta completato, l'amministratore lo setta a termiato
	@Column
	private Time orarioConsegna;
	
	@Column(nullable = false)
	private Time orarioPrevisto;
	
	@OneToOne
	private Domicilio indirizzoConsegna; //deve essere selezionato dal domicilio dell'utente
	   									 //però l'utente ha una lista di domicilii, come ci si arriva?
	
	@OneToOne
	private Cliente destinatario;
	
	@OneToMany(mappedBy="ordini", cascade= {CascadeType.PERSIST, CascadeType.REMOVE})	//in quanto è una composizione,
	private List<LineaOrdine> lineeOrdine;												//le operaizoni devono riflettersi sulle linee
	
	/*
	 * vedi operazione @SelezionaProdotto
	 * 
	 * l'oggetto ordine crea la linea d'ordine
	 * setta il prodotto e il quantitativo
	 * calcola il totale
	 * aggiunge la linea d'ordine nella sua lista
	 * @Matteo
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
	
	/*
	 * Se si vuole dare un costo di consegna ad ogni ordine a domicilio, ogni domicilio ha un costo di cossegna dipendente
	 * dal cap.
	 * Il valore del costo di consgna viene calcolato nel momento i cui si verifica che il domicilio rientri nel range del
	 * ristorante.
	 * @Matteo
	 */
	/*public void calcolcaTotaleConConsegna()
	{
		this.totale+=this.indirizzoConsegna.getCostoConsegna();
	}*/
	
	/*
	 * Vedi operazione @condermaOrdine Progetto @NuovoOrdineADomicilio
	 * @Matteo
	 */
	public void confermaOrdine(Cliente c)
	{
		this.setDestinatario(c);
	}

}
