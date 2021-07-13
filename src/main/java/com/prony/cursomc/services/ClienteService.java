package com.prony.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.prony.cursomc.domain.Cliente;
import com.prony.cursomc.dto.ClienteDTO;
import com.prony.cursomc.repositories.ClienteRepository;
import com.prony.cursomc.services.exceptions.DataIntegrityException;
import com.prony.cursomc.services.exceptions.ObjectNotFoundException;


@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepo;
	
	public Cliente find(Integer id) {
		Optional<Cliente> cliente = clienteRepo.findById(id);
		return cliente.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! id: " + id + 
				", Tipo: " + Cliente.class.getName()));
				
	}
	
	public Cliente insert(Cliente cliente) {
		cliente.setId(null);
		return clienteRepo.save(cliente);
	}

	public Cliente update(Cliente cliente) {
		Cliente clienteOriginal = find(cliente.getId());
		updateCliente(clienteOriginal, cliente);
		return clienteRepo.save(clienteOriginal);
	}
	
	private void updateCliente(Cliente clienteOriginal, Cliente cliente) {
		clienteOriginal.setNome(cliente.getNome());
		clienteOriginal.setEmail(cliente.getEmail());
	}

	public void delete(Integer id) {
		find(id);
		
		try {
			clienteRepo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um cliente que contém pedidos.");
		}
	}

	public List<Cliente> findAll() {
		List<Cliente> clientes = clienteRepo.findAll();
		return clientes;
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageReq = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return clienteRepo.findAll(pageReq);
	}
	
	public Cliente fromDTO(ClienteDTO clienteDto) {
		return new Cliente(clienteDto.getId(), clienteDto.getNome(), clienteDto.getEmail(), null, null);
	}
}
