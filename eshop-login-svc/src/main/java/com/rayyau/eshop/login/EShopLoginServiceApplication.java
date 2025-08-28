package com.rayyau.eshop.login;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableFeignClients
@ComponentScan(basePackages = {"com.rayyau.eshop.security.library", "com.rayyau.eshop.login"})
@EnableJpaRepositories(basePackages = "com.rayyau.eshop.security.library.repository")
@EntityScan(basePackages = "com.rayyau.eshop.security.library.dto")
public class EShopLoginServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EShopLoginServiceApplication.class, args);
	}

}
