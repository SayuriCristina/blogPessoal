package com.generation.blogpessoal.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table (name = "tb_tema") // Cria a tabela tema
public class Tema {
	
	// Gera o valor id, que é um Long, como identidade do dado.
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long Id;
	
	// Cria o ataributo título e torna ele obrigatório e necessariamentae entre 5 a 100 caracteres.
	@NotNull(message = "A descrição é obrigatória!")
	@Size (min = 5, max = 100, message = "A descrição deve conter no mínimo 5 e no máximo 100 caracteres")
	private String descricao;

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

}
