package com.theironyard.example.microservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class WebConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebConsumerApplication.class, args);
	}
}
