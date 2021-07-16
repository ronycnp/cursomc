package com.prony.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.prony.cursomc.domain.Categoria;
import com.prony.cursomc.domain.Produto;
import com.prony.cursomc.repositories.CategoriaRepository;
import com.prony.cursomc.repositories.ProdutoRepository;
import com.prony.cursomc.services.exceptions.ObjectNotFoundException;


@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepo;
	
	@Autowired
	private CategoriaRepository categoriaRepo;
	
	public Produto buscar(Integer id) {
		Optional<Produto> produto = produtoRepo.findById(id);
		return produto.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! id: " + id + 
				", Tipo: " + Produto.class.getName()));
	}
	
	public Page<Produto> findPage(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageReq = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = categoriaRepo.findAllById(ids);
		return produtoRepo.search(nome, categorias, pageReq);
	}
}
