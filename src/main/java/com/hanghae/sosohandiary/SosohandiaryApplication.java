package com.hanghae.sosohandiary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SosohandiaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(SosohandiaryApplication.class, args);
	}

}
