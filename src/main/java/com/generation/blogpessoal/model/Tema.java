package com.generation.blogpessoal.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity // Transforma em uma entidade, obriga criação de uma tabela 
@Table (name = "tb_tema") // Cria a tabela tema
public class Tema {
	
	// Gera automaticamente o valor id, que é um Long e implementa auto-incerement.
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long Id;
	
	// Cria o atributo título, torna ele obrigatório e com necessariamente entre 5 a 100 caracteres.
	@NotBlank(message = "A descrição é obrigatória!")
	@Size (min = 5, max = 100, message = "A descrição deve conter no mínimo 5 e no máximo 100 caracteres")
	private String descricao;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tema", cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties("tema")
	private List<Postagem> postagem;

	// Getters e Setters
	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Postagem> getPostagem() {
		return postagem;
	}

	public void setPostagem(List<Postagem> postagem) {
		this.postagem = postagem;
	}
	
}
