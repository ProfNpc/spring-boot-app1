package com.alessandro.app1.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.alessandro.app1.model.Produto;

public interface ProdutoRepository extends CrudRepository<Produto, Integer> {
	
	  List<Produto> findByDescricao(String descricao);

	  Produto findById(int id);
}
