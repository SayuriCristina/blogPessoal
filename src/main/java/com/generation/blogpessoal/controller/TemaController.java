package com.generation.blogpessoal.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


import com.generation.blogpessoal.model.Tema;
import com.generation.blogpessoal.repository.TemaRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/temas")
@CrossOrigin (origins = "*", allowedHeaders = "*") // Futuramente aceitará outras origens que não o localhost
public class TemaController {
	
	@Autowired
	private TemaRepository temaRepository;
	
	// Método GET: Ver todos, busca por id e tema
	
	// Ver todos
	@GetMapping 
	public ResponseEntity<List<Tema>> getAll(){
		return ResponseEntity.ok(temaRepository.findAll());
	}
	
	// Buscar por Id
	@GetMapping("/{id}") // localhost:8080/temas/1
	public ResponseEntity<Tema> getById(@PathVariable Long id){
		return temaRepository.findById(id) 
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	 // Buscar por tema
	@GetMapping ("/descricao/{descricao}") // localhost:8080/temas/descricao/a
	public ResponseEntity<List<Tema>> getByTema(@PathVariable String descricao){
		return ResponseEntity.ok(temaRepository.findAllByDescricaoContainingIgnoreCase(descricao));
	}
	
	// Método POST: Criar tema
	@PostMapping // localhost:8080/temas
	public ResponseEntity<Tema> post(@Valid @RequestBody Tema tema){
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(temaRepository.save(tema));
	}
	
	// Método PUT: Atualizar tema
	@PutMapping // localhost:8080/temas
	public ResponseEntity<Tema> put(@Valid @RequestBody Tema tema){
		return temaRepository.findById(tema.getId())
				.map(resposta -> ResponseEntity.status(HttpStatus.OK)
				.body(temaRepository.save(tema)))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
//	
	// Método DELETE: Apaga tema
	@DeleteMapping("/{id}") // localhost:8080/temas/1
	public void delete (@PathVariable Long id) {
		Optional<Tema> tema = temaRepository.findById(id);
		
		if(tema.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		temaRepository.deleteById(id);
		
	}
	
}
