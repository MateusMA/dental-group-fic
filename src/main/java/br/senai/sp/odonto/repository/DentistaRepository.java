package br.senai.sp.odonto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.senai.sp.odonto.model.Dentista;


public interface DentistaRepository extends JpaRepository<Dentista, Long>{
	
	// findBy buscar por um atributo da classe usando uma função do hibernate que recebe argumentos no que vem depois de findBy
	List<Dentista> findByCro(String cro);
	
	// Se o parametro do método for igual ao atributo buscado o @Param fica implicito na classe
	@Query(value = "SELECT d FROM Dentista as d WHERE d.nome LIKE %:nomeBusca%")
	List<Dentista> findByLikeNome(@Param("nomeBusca") String nome);
	
}
