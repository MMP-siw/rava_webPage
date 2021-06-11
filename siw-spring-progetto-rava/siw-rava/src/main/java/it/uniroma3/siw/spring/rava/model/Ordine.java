package it.uniroma3.siw.spring.rava.model;

import java.sql.Date; 
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.ModelAttribute;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter @Setter @AllArgsConstructor 
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
	private String tipologiaDiConsegna;		//può essere prima possibile o pianificata
	
	@Column
	private String orarioConsegna;
	
	/*
	 * @Column(nullable = false)
		private Time orarioPrevisto;
	*/
	@OneToOne
	private Domicilio indirizzoConsegna; //deve essere selezionato dal domicilio dell'utente
	   									 //però l'utente ha una lista di domicilii, come ci si arriva?
	
	@ManyToOne
	private Cliente utente;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="ordine")	//in quanto è una composizione,
	private List<LineaOrdine> lineeOrdine;												//le operaizoni devono riflettersi sulle linee
	
	
	//Ho inserito il cotruttore poichè in @Noargs, non crea la cllezione
	public Ordine ()
	{
		this.lineeOrdine=new ArrayList<>();
	}
	
	
	/*
	 * vedi operazione @SelezionaProdotto
	 * 
	 * l'oggetto ordine crea la linea d'ordine
	 * setta il prodotto e il quantitativo
	 * calcola il totale
	 * aggiunge la linea d'ordine nella sua lista
	 * @Matteo
	 */
	public LineaOrdine creaLineaOrdine(Prodotto p, int qnt)
	{
		LineaOrdine lio= new LineaOrdine();	//creazione della linea di prodotto
		lio.setProdotto(p);					//settaggio del prodotto associato alla linea d'ordine
		lio.setQuantita(qnt);				//settaggio del quantitativo selezionato
		lio.setOrdine(this);
		lio.setSubTotale(lio.getProdotto().getPrezzo()*qnt);	//settaggio del subtotale della linea(prezzoprodotto*qnt)
		
		
		this.aggoungiLineaOrdine(lio);
		
		 return lio;
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
	
	public void aggoungiLineaOrdine(LineaOrdine lio) 
	{
		this.totale+=lio.getSubTotale();
		this.lineeOrdine.add(lio);
		
		
	}


	/*
	 * Vedi operazione @condermaOrdine Progetto @NuovoOrdineADomicilio
	 * @Matteo
	 */
	public void confermaOrdine(Cliente c)
	{
		this.setUtente(c);	//
		c.addOrdine(this);
	}

	/*
	 * La logica dietro questo set è semplice:
	 * 
	 * CASE 1:
	 * Se il cliente inserisce "prima Possibile" l'input time è null
	 * Settiamo un orario di consegna indicativo( this.actualTime()+30/40 minuti (potrebbe essere migliorato associando
	 * per ogni cap un tempo stimato di consegna [30 minuti di preparazione + tempo previsto di consegna])
	 * 
	 * CASE 2:
	 * Se il cliente inserice "pianifica" e definisce un tempo di consegna 
	 * Settimao l'orario di consegna all'orario indicato dal cliente
	 * 
	 * In entrambi i casi il commento viene settato
	 * 
	 * Vedi caso d'uso @NuovoOrdineDomicilio passo @inserisciOrario
	 */
	public void setInfoFatturazione(String time, String commento) 
	{
		//Prima possibile
		if(time==null)
		{
			int  ora=LocalTime.now().plusMinutes(30).getHour();
			int minuti=LocalTime.now().plusMinutes(30).getMinute();
			String orario=new String(ora+":"+minuti);
			this.setOrarioConsegna(orario);
			
				
		}
		//pianificato
		else
		{
			this.setOrarioConsegna(time);
		}
		this.setCommento(commento);
		this.data =Date.valueOf(LocalDate.now());	//si assume che la consegna sia nello stesso giorno
		
		
	}
	
}
