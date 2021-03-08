package com.linecode.jcep;

import java.util.concurrent.ExecutionException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class JcepApplication {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		var applicationContext = SpringApplication.run(JcepApplication.class, args);
		var correiosService    = applicationContext.getBean(CorreiosService.class);

		correiosService.consultarCepAsync("49035620").whenComplete((endereco, exception) -> {
			if (exception != null) System.err.println(exception.getMessage());
			System.out.println(endereco.getBairro());
		});
	}
}


