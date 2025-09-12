package com.rayyau.eshop.pymt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableFeignClients
@ComponentScan(basePackages = {"com.rayyau.eshop.security.library", "com.rayyau.eshop.pymt"})
@EnableJpaRepositories(basePackages = {"com.rayyau.eshop.security.library.repository", "com.rayyau.eshop.pymt.repository"})
@EntityScan(basePackages = {"com.rayyau.eshop.security.library.dto", "com.rayyau.eshop.pymt.entity"})
public class EshopPymtServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EshopPymtServiceApplication.class, args);
	}

}
