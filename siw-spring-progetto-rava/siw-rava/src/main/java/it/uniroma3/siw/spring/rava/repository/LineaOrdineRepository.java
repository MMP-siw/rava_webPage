package it.uniroma3.siw.spring.rava.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.rava.model.LineaOrdine;
import it.uniroma3.siw.spring.rava.model.Ordine;

public interface LineaOrdineRepository extends CrudRepository<LineaOrdine,Long> 
{
	public List<LineaOrdine> findByOrdine(Ordine or);

}
