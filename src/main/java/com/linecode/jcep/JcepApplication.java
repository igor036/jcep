package com.linecode.jcep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class JcepApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(JcepApplication.class, args);

		var correiosService = new CorreiosService();
		var response = correiosService.consultarCep("49035620");

		System.out.println(response.getReturn().getBairro());
	}
}


