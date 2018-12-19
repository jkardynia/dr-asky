package com.jkgroup.drasky;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;

@SpringBootApplication(exclude = {ThymeleafAutoConfiguration.class})
public class DrAskyApplication {

	public static void main(String[] args) {
		SpringApplication.run(DrAskyApplication.class, args);
	}
}
