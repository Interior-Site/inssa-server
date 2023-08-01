package com.inssa.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class InssaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(InssaServerApplication.class, args);
	}

}
