package com.alessandro.app1.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alessandro.app1.model.Produto;
import com.alessandro.app1.repository.ProdutoRepository;

@Controller
public class ProdutoController {
	
	@Autowired
	private ProdutoRepository repository;
	
	private static List<Produto> listaProdutos = new ArrayList<Produto>();

	private static int next = 1;
	
	static {
		listaProdutos.add(new Produto(next++, "Pão", "Pão Francês", 2.56));
		listaProdutos.add(new Produto(next++, "Sabão", "Sabão glicerinado", 3.56));
	}
	
	@GetMapping("/produto/list")
	public String list(Model model) {
		
		//model.addAttribute("produtos", listaProdutos);
		model.addAttribute("produtos", repository.findAll());
		
		return "lista-produtos";
	}
	
	@GetMapping("/produto/{id}")
	public String detalhe(
			@PathVariable("id") int id,
			Model model) {
		
		//Produto p = buscarProdutoPeloId(id);
		Produto p = repository.findById(id);
	
		if (p == null) {
			return "produto-nao-encontrado";
		}
		
		model.addAttribute("produto", p);
		
		return "detalhe-produto";
	}

	private Produto buscarProdutoPeloId(int id) {
		for (Produto p : listaProdutos) {
			if (p.getId() == id) {
				return p;
			}
		}
		return null;
	}


	@GetMapping("/produto/novo")
	public String novo(Model model) {
		model.addAttribute("produto", new Produto());
		return "novo-produto";
	}
	
	@PostMapping("/produto/novo")
	public ModelAndView novo(Produto prod) {
		
		ModelAndView mv = 
				new ModelAndView("redirect:/produto/list");
	
		if (prod.getId() == null) {
			
			//prod.setId(next++);
			//listaProdutos.add(prod);
			repository.save(prod);
			
		} else {
			
			//updateProduto(prod);
			repository.save(prod);
		}
		
		return mv;
	}

	private void updateProduto(Produto prod) {
		ListIterator<Produto> i = listaProdutos.listIterator();
		while(i.hasNext()) {
			Produto atual = i.next(); 
			if (atual.getId() == prod.getId()) {
				i.set(prod);
			}
		}
	}

	
	@GetMapping("/produto/{id}/edit")
	public String edit(
			@PathVariable("id") int id, Model model) {
		
		//Produto p = buscarProdutoPeloId(id);
		Produto p = repository.findById(id);
		
		if (p == null) {
			return "produto-nao-encontrado";
		}
		
		model.addAttribute("produto", p);
		return "novo-produto";
	}

}
