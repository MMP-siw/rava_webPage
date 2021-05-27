package it.uniroma3.siw.spring.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.model.Cliente;

public interface ClienteRepository extends CrudRepository<Cliente,Long>
{

}
