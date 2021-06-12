package it.uniroma3.siw.spring.rava.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import it.uniroma3.siw.spring.rava.model.FasciaOraria;
import it.uniroma3.siw.spring.rava.model.Slot;

public interface SlotRepository extends CrudRepository<Slot,Long> 
{
	
	public List<Slot> findByDataAndFasciaOraria(LocalDate data, FasciaOraria fasciaOraria);
	

}
