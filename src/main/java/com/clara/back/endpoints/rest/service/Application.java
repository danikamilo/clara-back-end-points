package com.clara.back.endpoints.rest.service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Autor Daniel Camilo
 */
@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "API de Artistas",
				version = "1.0",
				description = "API para gestión de artistas y comparaciones"
		)
)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
