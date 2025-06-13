package com.prueba.authPrueba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AuthPruebaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthPruebaApplication.class, args);
	}

}
