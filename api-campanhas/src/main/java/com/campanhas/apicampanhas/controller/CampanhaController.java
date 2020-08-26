package com.campanhas.apicampanhas.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.campanhas.apicampanhas.model.Campanha;
import com.campanhas.apicampanhas.model.CampanhaResponse;
import com.campanhas.apicampanhas.repository.CampanhaRepository;

/**
 * 
 * @author João
 *
 */

@RestController
@RequestMapping(value = "/api")
public class CampanhaController {
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	private static SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
	
	@Autowired
	CampanhaRepository campanhaRepository;
	
	@GetMapping(value = "/campanhas")
	public List<CampanhaResponse> getCampanhas() throws ParseException {
		String atual = sdf.format(new Date());
		Date aux = sdf.parse(atual);
		Timestamp limite = new Timestamp(aux.getTime());
		List<Campanha> list = campanhaRepository.findAllBeforeToday(limite);
		List<CampanhaResponse> campanhas = new LinkedList<CampanhaResponse>();
		list.forEach( elemento -> {
			CampanhaResponse cResponse = new CampanhaResponse();
			cResponse.setId(elemento.getId());
			cResponse.setNome(elemento.getNome());
			cResponse.setTime(elemento.getTime());
			String vigencia = "Inicio em " + sdf2.format(elemento.getInicio())
			 + ", fim em " + sdf2.format(elemento.getFim());
			cResponse.setVigencia(vigencia);
			campanhas.add(cResponse);
		});
		return campanhas;
	}
	
	@GetMapping(value = "/campanha/{id}")
	public ResponseEntity<?> getCampanha(@PathVariable(value = "id") long id) throws ParseException {
		String responseError = "Falha ao encontrar campanha"; 
		CampanhaResponse response = new CampanhaResponse();
		try {
			String atual = sdf.format(new Date());
			Date aux = sdf.parse(atual);
			Timestamp limite = new Timestamp(aux.getTime());
			Campanha campanha = campanhaRepository.findByIdBeforeToday(id, limite);
			response.setNome(campanha.getNome());
			response.setTime(campanha.getTime());
			String virgencia = "Inicio em " + sdf2.format(campanha.getInicio())
			 + ", fim em " + sdf2.format(campanha.getFim());
			response.setVigencia(virgencia);
		} catch ( Exception e ) {
			return ResponseEntity.badRequest().body(responseError);
		}
		return ResponseEntity.accepted().body(response);
	}
	
	@PostMapping(value = "/campanha")
	public ResponseEntity<?> saveCampanha(@RequestBody Campanha campanha) throws ParseException {		
		List<Campanha> allCampanhas = campanhaRepository.findAll();
		campanha = campanhaRepository.save(campanha);
		
		List<CampanhaResponse> campanhasResponse = new LinkedList<CampanhaResponse>();

		
		if ( allCampanhas.size() == 0 ) {
			CampanhaResponse response = new CampanhaResponse();
			response.setNome(campanha.getNome());
			response.setTime(response.getTime());
			String vigencia = "Inicio em " + sdf2.format(campanha.getInicio())
			 + ", fim em " + sdf2.format(campanha.getFim());
			response.setVigencia(vigencia);
			return ResponseEntity.accepted().body(response);
		}
		
		int day = 1;
		int atualizacoes = 0;
		Campanha registro = new Campanha();
		while ( true ) {
			for (int i=0; i<allCampanhas.size(); i++) {
				registro = allCampanhas.get(i);
				if ( sdf2.format(registro.getFim()).equals(sdf2.format(campanha.getFim())) && registro.getId() != campanha.getId() ) {
					
					Date date = registro.getFim();			
					Calendar cal = Calendar.getInstance();
					cal.setTime(date);
					cal.add(Calendar.DAY_OF_MONTH, +day);
					date = cal.getTime();
					registro.setFim(new Timestamp(date.getTime()));
					
					campanhaRepository.save(registro);
					
					CampanhaResponse response = new CampanhaResponse();
					response.setNome(registro.getNome());
					response.setTime(registro.getTime());
					String vigencia = "Inicio em " + sdf2.format(registro.getInicio())
					 + ", fim em " + sdf2.format(registro.getFim());
					response.setVigencia(vigencia);
					campanhasResponse.add(response);
					
					atualizacoes++;
					campanha = registro;
				}
				if ( atualizacoes > 0 )
					break;
			}
			if ( atualizacoes == 0 )
				break;
			atualizacoes = 0;
		}
		return ResponseEntity.accepted().body(campanhasResponse);
	}
	
	@PutMapping(value = "/campanha")
	public ResponseEntity<?> updateCampanha(@RequestBody Campanha campanha) {
		CampanhaResponse response = new CampanhaResponse();
		try {
			campanha = campanhaRepository.save(campanha);
			response.setNome(campanha.getNome());
			response.setTime(campanha.getTime());
			String vigencia = "Inicio em " + sdf2.format(campanha.getInicio())
			 + ", fim em " + sdf2.format(campanha.getFim());
			response.setVigencia(vigencia);
		} catch ( Exception e ) {
			return ResponseEntity.badRequest().body("Falha ao atualizar os dados da campanha!");
		}
		return ResponseEntity.accepted().body(response);
	}
	
	@DeleteMapping(value = "/campanha/{id}")
	public ResponseEntity<?> deleteCampanha(@PathVariable(value = "id") long id) {
		try {
			campanhaRepository.deleteById(id);
		} catch ( Exception e ) {
			return ResponseEntity.badRequest().body("Falha ao excluir campanha");
		}
		return ResponseEntity.accepted().body("Ação realizada com sucesso!");
	}
}