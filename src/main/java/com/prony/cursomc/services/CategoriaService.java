package com.prony.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.prony.cursomc.domain.Categoria;
import com.prony.cursomc.dto.CategoriaDTO;
import com.prony.cursomc.repositories.CategoriaRepository;
import com.prony.cursomc.services.exceptions.DataIntegrityException;
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
	
	public void delete(Integer id) {
		find(id);
		
		try {
			categoriaRepo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que contém produtos.");
		}
	}

	public List<Categoria> findAll() {
		List<Categoria> categorias = categoriaRepo.findAll();
		return categorias;
	}
	
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageReq = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return categoriaRepo.findAll(pageReq);
	}
	
	public Categoria fromDTO(CategoriaDTO categoriaDto) {
		return new Categoria(categoriaDto.getId(), categoriaDto.getNome());
	}
}
