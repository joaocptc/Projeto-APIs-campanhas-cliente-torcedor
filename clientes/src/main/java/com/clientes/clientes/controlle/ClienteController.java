package com.clientes.clientes.controlle;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.clientes.clientes.model.Campanha;
import com.clientes.clientes.model.CampanhasList;
import com.clientes.clientes.model.Cliente;
import com.clientes.clientes.model.ClientesCampanhas;
import com.clientes.clientes.repository.ClienteRepository;
import com.clientes.clientes.repository.ClientesCampanhasRepository;

/**
 * 
 * @author João
 *
 */

@RestController
@RequestMapping(value = "/api")
public class ClienteController {

	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	ClientesCampanhasRepository clientesCampanhasRepository;
	
	@GetMapping(value = "/clientes")
	public List<Cliente> getClientes(){
		return clienteRepository.findAll();
	}
	
	@PostMapping(value = "/cliente")
	public ResponseEntity<?> saveCliente(@RequestBody Cliente cliente) {
		String responseMsg = "";
		List<Cliente> cadastrado = clienteRepository.getClienteByEmail(cliente.getEmail());
		if ( cadastrado.size() == 0 ) {
			responseMsg = "cliente cadastrado sucesso!";
			cliente = clienteRepository.save(cliente);
		} else
			responseMsg = "Cliente já cadastrado!";
		
		List<ClientesCampanhas> campanhasVinculadas = clienteRepository.getCampanhasRelacionadas(cliente.getEmail());
		
		List<Campanha> campanhas = new LinkedList<Campanha>();
		
		RestTemplate restTemp = new RestTemplate();
		String resourceURI = "http://localhost/api/campanhas";
		try {
			CampanhasList response = restTemp.getForObject(resourceURI, CampanhasList.class);
			campanhas = response.getCampanhas();
			ResponseEntity.accepted().header("mensagem", responseMsg).body(campanhas);
		} catch ( Exception e ) {
			ResponseEntity.badRequest().body("Falha ao buscar campanhas na API externa!");
		}
		
		if ( campanhas.size() == 0 )
			return ResponseEntity.accepted().body(responseMsg + " Não foram encontradas campanhas para associar!");
		
		List<Campanha> novasCampanhas = new LinkedList<Campanha>();
		
		if ( cadastrado.size() == 0 ) {
			for ( Campanha camp : campanhas ) {
				if ( camp.getTime() == cliente.getTime() ) { // valida que apenas campanhas do time do coração sejam inseridas 
					ClientesCampanhas relClienteCampanha = new ClientesCampanhas();
					relClienteCampanha.setCliente(cadastrado.get(0).getId());
					relClienteCampanha.setCampanha(camp.getId());
					clientesCampanhasRepository.save(relClienteCampanha);
					novasCampanhas.add(camp);
				}
			}
		} else {
			boolean existe = false;
			for ( Campanha camp : campanhas ) {
				existe = false;
				for ( ClientesCampanhas campVinculada : campanhasVinculadas ) {
					if (camp.getId() == campVinculada.getCampanha()) {
						existe = true;
						break;
					}
				}
				if ( !existe ) {
					ClientesCampanhas relClienteCampanha = new ClientesCampanhas();
					relClienteCampanha.setCliente(cadastrado.get(0).getId());
					relClienteCampanha.setCampanha(camp.getId());
					clientesCampanhasRepository.save(relClienteCampanha);
					novasCampanhas.add(camp);
				}
			}
		}
		return ResponseEntity.accepted().body(novasCampanhas);
	}
}