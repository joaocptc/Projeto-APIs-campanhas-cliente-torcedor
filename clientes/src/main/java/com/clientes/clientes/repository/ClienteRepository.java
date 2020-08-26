package com.clientes.clientes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.clientes.clientes.model.Cliente;
import com.clientes.clientes.model.ClientesCampanhas;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	
	@Query(value = "SELECT 1 FROM TB_CLIENTE WHERE email = :email", nativeQuery = true)
	List<Cliente> getClienteByEmail(@Param("email") String email);

	@Query(value = "SELECT CAM.* FROM TB_CLIENTES_CAMPANHAS CAM INNER JOIN TB_CLIENTE CLI " 
				 + "ON CAM.cliente = CLI.id WHERE CLI.email = :email", nativeQuery = true)
	List<ClientesCampanhas> getCampanhasRelacionadas(@Param("email") String email);
	
	Cliente findById(long id);
}
