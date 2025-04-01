package com.hotel_transylvania.App;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.hotel_transylvania")
@EntityScan(basePackages = {
	    "com.hotel_transylvania.entities",
	    "com.hotel_transylvania.model"  
	})
@EnableJpaRepositories(basePackages = "com.hotel_transylvania.repositories")
public class HotelTransylvaniaApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelTransylvaniaApplication.class, args);
	}
}
