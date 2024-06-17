package com.votacao.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI customOpenAPI() {
		return new OpenAPI().info(new Info().title("Aplicação votacao-pauta API")
				.description("Endpoints disponíveis na aplicação votacao-pauta.")
				.contact(new Contact().email("clovis.gargione@gmail.com").name("Developer: Clovis Gargione"))
				.version("1.0"));
	}
}
