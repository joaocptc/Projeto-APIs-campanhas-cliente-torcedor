package com.clientes.clientes.model;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author João
 *
 */

public class CampanhasList {
	
	private List<Campanha> campanhas;
	
	public CampanhasList() {
		campanhas = new LinkedList<Campanha>();
	}
	
	public List<Campanha> getCampanhas() {
		return campanhas;
	}

	public void setCampanhas(List<Campanha> campanhas) {
		this.campanhas = campanhas;
	}
}
