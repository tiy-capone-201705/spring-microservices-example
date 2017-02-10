package com.theironyard.example.microservices;

import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.support.ServletContextScope;

import com.theironyard.example.microservices.config.JerseyConfig;

@SpringBootApplication
public class RestfulMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestfulMicroserviceApplication.class, args);
	}
	
	@Bean
	public ServletRegistrationBean jerseyConfig() {
		ServletRegistrationBean registration = new ServletRegistrationBean(new ServletContainer(), "/*");
		registration.addInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS, JerseyConfig.class.getName());
		return registration;
	}
}
