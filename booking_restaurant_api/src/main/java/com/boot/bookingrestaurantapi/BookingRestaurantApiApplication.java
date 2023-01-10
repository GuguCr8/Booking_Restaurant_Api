package com.boot.bookingrestaurantapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.boot.bookingrestaurantapi.repositories")
@SpringBootApplication
public class BookingRestaurantApiApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(BookingRestaurantApiApplication.class, args);

	}

}
