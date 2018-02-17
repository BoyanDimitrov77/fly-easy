package com.fly.easy.flyeasy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.ozimov.springboot.mail.configuration.EnableEmailTools;

@SpringBootApplication
@EnableEmailTools
//@ComponentScan(basePackages = {"com.fly.easy.flyeasy","it.ozimov.springboot"})
public class FlyEasyApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlyEasyApplication.class, args);
	}
}
