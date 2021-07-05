package com.prony.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prony.cursomc.domain.Pedido;
import com.prony.cursomc.repositories.PedidoRepository;
import com.prony.cursomc.services.exceptions.ObjectNotFoundException;


@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepo;
	
	public Pedido buscar(Integer id) {
		Optional<Pedido> pedido = pedidoRepo.findById(id);
		return pedido.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! id: " + id + 
				", Tipo: " + Pedido.class.getName()));
	}
}
