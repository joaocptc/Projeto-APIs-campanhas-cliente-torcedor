package com.clientes.clientes.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_CLIENTES_CAMPANHAS")
public class ClientesCampanhas implements Serializable {

	private static long serialVersionUID = 1L;
	
	@Id
	@Column(name = "registro")
	private long registro;
	
	@Column(name = "cliente")
	private long cliente;
	
	@Column(name = "campanha")
	private long campanha;
	
	public long getRegistro() {
		return registro;
	}

	public void setRegistro(long registro) {
		this.registro = registro;
	}

	public long getCliente() {
		return cliente;
	}

	public void setCliente(long cliente) {
		this.cliente = cliente;
	}

	public long getCampanha() {
		return campanha;
	}

	public void setCampanha(long campanha) {
		this.campanha = campanha;
	}
}
