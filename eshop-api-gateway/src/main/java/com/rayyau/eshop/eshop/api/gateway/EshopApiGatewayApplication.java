package com.rayyau.eshop.eshop.api.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableFeignClients(basePackages = {
        "com.rayyau.eshop.eshop.api.gateway",
        "com.rayyau.eshop.payment.library.client",
        "com.rayyau.eshop.security.library.client"
})
@ComponentScan(basePackages = {"com.rayyau.eshop.security.library", "com.rayyau.eshop.eshop.api.gateway", "com.rayyau.eshop.payment.library"})
@EnableJpaRepositories(basePackages = "com.rayyau.eshop.security.library.repository")
@EntityScan(basePackages = "com.rayyau.eshop.security.library.dto")
public class EshopApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(EshopApiGatewayApplication.class, args);
	}

}
