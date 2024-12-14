package com.proje.healpoint.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = {"com.proje.healpoint"})
@EntityScan(basePackages = {"com.proje.healpoint"})
@EnableJpaRepositories(basePackages = {"com.proje.healpoint"})
public class HealpointApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealpointApplication.class, args);
	}

}
