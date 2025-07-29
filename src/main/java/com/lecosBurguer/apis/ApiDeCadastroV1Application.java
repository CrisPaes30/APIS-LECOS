package com.lecosBurguer.apis;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@OpenAPIDefinition(
		info = @Info(title = "API de Cadastro", version = "1.0", description = "Documentação da API de Cadastro")
)
@SpringBootApplication
@ComponentScan("com.lecosBurguer.apis")
@EnableFeignClients
public class ApiDeCadastroV1Application {

	public static void main(String[] args) {
		SpringApplication.run(ApiDeCadastroV1Application.class, args);
	}

}
