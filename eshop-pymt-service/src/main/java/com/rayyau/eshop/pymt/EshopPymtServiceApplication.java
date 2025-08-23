package com.rayyau.eshop.pymt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EshopPymtServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EshopPymtServiceApplication.class, args);
	}

}
