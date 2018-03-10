package com.fly.easy.flyeasy;

import java.util.Date;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.ozimov.springboot.mail.configuration.EnableEmailTools;

@SpringBootApplication
@EnableEmailTools
//@ComponentScan(basePackages = {"com.fly.easy.flyeasy","it.ozimov.springboot"})
public class FlyEasyApplication {

	@PostConstruct
    public void init(){
        TimeZone.setDefault(TimeZone.getDefault());   // It will set UTC timezone
        System.out.println("Spring boot application running in UTC timezone :"+new Date());   // It will print UTC timezone
    }

	public static void main(String[] args) {
		SpringApplication.run(FlyEasyApplication.class, args);
	}
}
