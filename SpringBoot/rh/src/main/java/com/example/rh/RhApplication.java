package com.example.rh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.rh")
public class RhApplication {

	public static void main(String[] args) {
		SpringApplication.run(RhApplication.class, args);
	}

}
