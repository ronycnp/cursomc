package com.prony.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.prony.cursomc.domain.Cliente;
import com.prony.cursomc.dto.ClienteDTO;
import com.prony.cursomc.dto.ClienteNewDTO;
import com.prony.cursomc.services.ClienteService;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService clienteService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> listarTodos() {
		List<Cliente> clientes = clienteService.findAll();
		List<ClienteDTO> clientesDto = clientes.stream()
											.map(cliente -> new ClienteDTO(cliente))
											.collect(Collectors.toList());
		
		return ResponseEntity.ok().body(clientesDto);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Cliente> listar(@PathVariable Integer id) {
		Cliente cliente = clienteService.find(id);
		
		return ResponseEntity.ok().body(cliente);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> inserir(@Valid @RequestBody ClienteNewDTO clienteNewDto) {
		Cliente cliente = clienteService.fromDTO(clienteNewDto);
		cliente = clienteService.insert(cliente);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
											.path("/{id}")
											.buildAndExpand(cliente.getId())
											.toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> atualizar(@Valid @RequestBody ClienteDTO clienteDto, @PathVariable Integer id) {
		Cliente cliente = clienteService.fromDTO(clienteDto);
		cliente.setId(id);
		cliente = clienteService.update(cliente);
		
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> apagar(@PathVariable Integer id) {
		clienteService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> listarPaginado(
			@RequestParam(value = "page", defaultValue = "0") Integer page, 
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage, 
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy, 
			@RequestParam(value = "page", defaultValue = "ASC" )String direction) {
		Page<Cliente> clientes = clienteService.findPage(page, linesPerPage, orderBy, direction);
		Page<ClienteDTO> clientesDto = clientes.map(cliente -> new ClienteDTO(cliente));
		
		return ResponseEntity.ok().body(clientesDto);
	}

}
