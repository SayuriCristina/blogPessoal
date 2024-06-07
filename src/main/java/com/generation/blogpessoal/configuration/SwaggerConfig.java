package com.generation.blogpessoal.configuration;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;

@Configuration
public class SwaggerConfig {

	@Bean
	OpenAPI springBlogPessoalOpenAPI() {
		return new OpenAPI()
				.info(new Info().title("Projeto Blog Pessoal")
						.description("Projeto de blog desenvolvido na Generation Brasil com backend "
								+ "completo, contendo funções de CRUD, segurança e testes automatizados.")
						.version("v0.0.1")
						.license(new License()
								.name("Sayuri Cristina Souza e Silva")
								.url("https://www.linkedin.com/in/sayuri-cristina"))
						.contact(new Contact()
								.name("Sayuri Cristina")
								.url("https://github.com/SayuriCristina")
								.email("sayuri.cristinass@gmail.com")))
				.externalDocs(new ExternalDocumentation()
						.description("GitHub")
						.url("https://github.com/SayuriCristina/blogPessoal"));
		
	}

	@Bean
	OpenApiCustomizer customerGlobalHeaderOpenApiCustomiser() {
		return openApi -> {
			openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations().forEach(operation -> {
				ApiResponses apiResponses = operation.getResponses();
				
				apiResponses.addApiResponse("200", createApiResponse("Sucesso!"));
				apiResponses.addApiResponse("201", createApiResponse("Objeto persistido!"));
				apiResponses.addApiResponse("204", createApiResponse("Objeto excluido!"));
				apiResponses.addApiResponse("400", createApiResponse("Erro na requisição"));
				apiResponses.addApiResponse("401", createApiResponse("Acesso não autorizado!"));
				apiResponses.addApiResponse("403", createApiResponse("Acesso proibido!"));
				apiResponses.addApiResponse("404", createApiResponse("Objeto não encontrado"));
				apiResponses.addApiResponse("500", createApiResponse("Erro na aplicação!"));
				
			}));
		};
	}

	private ApiResponse createApiResponse(String message) {
		return new ApiResponse().description(message);
	}
	
}
