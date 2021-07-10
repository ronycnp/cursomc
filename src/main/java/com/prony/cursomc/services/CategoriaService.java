package com.prony.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prony.cursomc.domain.Categoria;
import com.prony.cursomc.repositories.CategoriaRepository;
import com.prony.cursomc.services.exceptions.ObjectNotFoundException;


@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepo;
	
	public Categoria find(Integer id) {
		Optional<Categoria> categoria = categoriaRepo.findById(id);
		return categoria.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! id: " + id + 
				", Tipo: " + Categoria.class.getName()));
				
	}
	
	public Categoria insert(Categoria categoria) {
		categoria.setId(null);
		return categoriaRepo.save(categoria);
	}

	public Categoria update(Categoria categoria) {
		find(categoria.getId());
		return categoriaRepo.save(categoria);
	}
}
