package com.puresoftware.raymondJames;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@OpenAPIDefinition(info=@Info(title="Camunda APIs"))

@SpringBootApplication
public class RaymondJamesApplication {

	@Bean
	public RestTemplate restTemplate () {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(RaymondJamesApplication.class, args);
	}

}
