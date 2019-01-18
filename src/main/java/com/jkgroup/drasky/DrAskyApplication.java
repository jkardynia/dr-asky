package com.jkgroup.drasky;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@SpringBootApplication(exclude = {ThymeleafAutoConfiguration.class})
@EnableCaching
@RestController
public class DrAskyApplication {

	public static void main(String[] args) {
		Locale.setDefault(Locale.ENGLISH);
		SpringApplication.run(DrAskyApplication.class, args);
	}

	@GetMapping("readiness_check")
	public String readinessCheck(){
		return "OK";
	}
}
