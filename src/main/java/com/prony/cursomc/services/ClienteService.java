package com.prony.cursomc.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.prony.cursomc.domain.Cidade;
import com.prony.cursomc.domain.Cliente;
import com.prony.cursomc.domain.Endereco;
import com.prony.cursomc.domain.enums.TipoCliente;
import com.prony.cursomc.dto.ClienteDTO;
import com.prony.cursomc.dto.ClienteNewDTO;
import com.prony.cursomc.repositories.ClienteRepository;
import com.prony.cursomc.repositories.EnderecoRepository;
import com.prony.cursomc.services.exceptions.DataIntegrityException;
import com.prony.cursomc.services.exceptions.ObjectNotFoundException;


@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepo;
	
	@Autowired
	private EnderecoRepository enderecoRepo;
	
	public Cliente find(Integer id) {
		Optional<Cliente> cliente = clienteRepo.findById(id);
		return cliente.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! id: " + id + 
				", Tipo: " + Cliente.class.getName()));
				
	}
	
	@Transactional
	public Cliente insert(Cliente cliente) {
		cliente.setId(null);
		cliente = clienteRepo.save(cliente);
		enderecoRepo.saveAll(cliente.getEnderecos());
		return cliente;
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
	
	public Cliente fromDTO(ClienteNewDTO clienteDto) {
		Cliente cli = new Cliente(null, clienteDto.getNome(), clienteDto.getEmail(), clienteDto.getCpfCnpj(), TipoCliente.toEnum(clienteDto.getTipo()));
		Cidade cid = new Cidade(clienteDto.getCidadeId(), null, null);
		Endereco end = new Endereco(null, clienteDto.getLogradouro(), clienteDto.getNumero(), clienteDto.getComplemento(), clienteDto.getBairro(), clienteDto.getCep(), cli, cid);
		
		cli.getEnderecos().add(end);
		
		cli.getTelefones().add(clienteDto.getTelefone1());
		if(clienteDto.getTelefone2() != null)
			cli.getTelefones().add(clienteDto.getTelefone2());
		if(clienteDto.getTelefone3() != null)
			cli.getTelefones().add(clienteDto.getTelefone3());
		
		return cli;
	}
}
