package com.proje.healpoint.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"com.proje.healpoint"})
public class HealpointApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealpointApplication.class, args);
	}

}
