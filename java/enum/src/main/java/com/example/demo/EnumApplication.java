package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.demo.Planet;

@SpringBootApplication
public class EnumApplication {

	public static void main(String[] args) {
		System.out.println(Planet.MERCURY.getMass());
		SpringApplication.run(EnumApplication.class, args);
	}

}
