package com.generation.blogpessoal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.repository.UsuarioRepository;
import com.generation.blogpessoal.service.UsuarioService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT) // coloca o teste em uma porta aleatoria
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // agrupa todos os testes
public class UsuarioControllerTest {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private TestRestTemplate testRestTemplate;

	// Declara que antes de tudo, serão deletados todos os usuários e criado um
	// chamado root.
	@BeforeAll
	void start() {
		usuarioRepository.deleteAll();

		usuarioService.cadastrarUsuario(new Usuario(0L, "root", "root@root.com", "rootroot", ""));
	}

	// Teste de criação de usuario, onde HTTPENTITY declara o corpo da requisição
	@Test
	@DisplayName("Deve cadastrar um novo usuário")
	public void criarNovoUsuario() {
		HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(
				new Usuario(0L, "user", "user@user.com", "useruser", "")); // Usuario(id, nome, email, senha, foto)

		// Entrega o body (corpoRequisicao) no endereço "/usuarios/cadastrar", como o
		// método POST no padrão Usuario.class
		ResponseEntity<Usuario> corpoResposta = testRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST,
				corpoRequisicao, Usuario.class); // testRestTemplate.exchange(endereço, metodo http, nome do body,
													// padrão desejado)

		assertEquals(HttpStatus.CREATED, corpoResposta.getStatusCode());

	}

	@Test
	@DisplayName("Não deve permitir duplicação do Usuário")
	public void naoDuplicarUsuario() {

		usuarioService.cadastrarUsuario(new Usuario(0L, "Usuário", "usuario@usuario.com", "usuariousuario", ""));

		HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(
				new Usuario(0L, "Usuário Duplicado", "usuario@usuario.com", "13465278", ""));

		ResponseEntity<Usuario> corpoResposta = testRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST,
				corpoRequisicao, Usuario.class);

		assertEquals(HttpStatus.BAD_REQUEST, corpoResposta.getStatusCode());
	}

	@Test
	@DisplayName("Deve atualizar o usuário")
	public void atualizarUsuario() {
		Optional<Usuario> usuarioCadastrado = usuarioService
				.cadastrarUsuario(new Usuario(0L, "Usuário", "usuario123@usuario.com", "usuariousuario", ""));

		Usuario usuarioUpdate = new Usuario(usuarioCadastrado.get().getId(), "Usuário Atualizado",
				"usuario12@usuario.com", "usuario1!", "");

		HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(usuarioUpdate);

		// mesmo login criado no start() irá autenticar a aplicação
		ResponseEntity<Usuario> corpoResposta = testRestTemplate.withBasicAuth("user@user.com", "useruser")
				.exchange("/usuarios/atualizar", HttpMethod.PUT, corpoRequisicao, Usuario.class);

		assertEquals(HttpStatus.OK, corpoResposta.getStatusCode());
	}

	@Test
	@DisplayName("Deve mostrar todos os usuários")
	public void mostrarTodosUsuarios() {
		usuarioService.cadastrarUsuario(new Usuario(0L, "Número 1", "usuario1@usuario.com", "usuariousuario", ""));

		usuarioService.cadastrarUsuario(new Usuario(0L, "Número 2", "usuario2@usuario.com", "usuariousuario", ""));

		usuarioService.cadastrarUsuario(new Usuario(0L, "Número 3", "usuario3@usuario.com", "usuariousuario", ""));

		ResponseEntity<String> resposta = testRestTemplate.withBasicAuth("user@user.com", "useruser")
				.exchange("/usuarios/all", HttpMethod.GET, null, String.class);

		assertEquals(HttpStatus.OK, resposta.getStatusCode());
	}
}
