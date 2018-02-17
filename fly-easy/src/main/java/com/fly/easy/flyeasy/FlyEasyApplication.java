package com.fly.easy.flyeasy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan(basePackages = {"com.flight.easy.flighteasy"})
public class FlyEasyApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlyEasyApplication.class, args);
	}
}
