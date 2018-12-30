package com.jkgroup.drasky;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(exclude = {ThymeleafAutoConfiguration.class})
@EnableCaching
public class DrAskyApplication {

	public static void main(String[] args) {
		SpringApplication.run(DrAskyApplication.class, args);
	}
}
