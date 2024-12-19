package com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.lecosBurguer.api.cadastro")
public class ApiDeCadastroV1Application {

	public static void main(String[] args) {
		SpringApplication.run(ApiDeCadastroV1Application.class, args);
	}

}
