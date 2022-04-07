package br.com.dbc.vemser.ifsultroopers.trabalhofinalmodulo3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TrabalhoFinalModulo3Application {

	public static void main(String[] args) {
		SpringApplication.run(TrabalhoFinalModulo3Application.class, args);
	}

}
