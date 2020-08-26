package com.campanhas.apicampanhas.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.campanhas.apicampanhas.model.Campanha;

public interface CampanhaRepository extends JpaRepository<Campanha, Long> {
	
	@Query(value = "SELECT id, nome, time, inicio, fim FROM TB_CAMPANHA WHERE fim > ?1", nativeQuery = true)
	List<Campanha> findAllBeforeToday(Timestamp data);
	
	@Query(value = "SELECT id, nome, time, inicio, fim FROM TB_CAMPANHA WHERE fim = ?1", nativeQuery = true)
	List<Campanha> findAllEndDateEqualsParameter(Timestamp data);
	
	Campanha findById(long id);
	
	@Query(value = "SELECT id, nome, time, inicio, fim FROM TB_CAMPANHA WHERE id = ?1 AND fim > ?2", nativeQuery = true)
	Campanha findByIdBeforeToday(long id, Timestamp data); 
}
