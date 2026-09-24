package com.example.dizimo.paroquial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.example.dizimo.paroquial", "com.devshowcase.api"})
@EntityScan("com.devshowcase.api.model")
@EnableJpaRepositories("com.devshowcase.api.repository")
public class DizimoParoquialApplication {

	public static void main(String[] args) {
		SpringApplication.run(DizimoParoquialApplication.class, args);
	}

}
