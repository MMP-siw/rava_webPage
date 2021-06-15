package it.uniroma3.siw.spring.rava.repository;

import java.lang.annotation.Native;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.rava.model.LineaOrdine;
import it.uniroma3.siw.spring.rava.model.Ordine;

public interface LineaOrdineRepository extends CrudRepository<LineaOrdine,Long> 
{
	public List<LineaOrdine> findByOrdine(Ordine or);

	
	@Query(value="delete l from LineaOrdine l where l.ordine=:eliminare", nativeQuery=true)
	public void deleteForOrdine(Ordine eliminare);

}
