package com.prony.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prony.cursomc.domain.Categoria;
import com.prony.cursomc.repositories.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepo;
	
	public Categoria buscar(Integer id) {
		Optional<Categoria> categoria = categoriaRepo.findById(id);
		return categoria.orElse(null);
	}
}
