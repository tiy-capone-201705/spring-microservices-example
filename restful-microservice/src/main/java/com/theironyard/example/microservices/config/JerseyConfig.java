package com.theironyard.example.microservices.config;

import com.theironyard.example.microservices.controllers.RestMicroservice;

import org.glassfish.jersey.server.ResourceConfig;

public class JerseyConfig extends ResourceConfig {
	public JerseyConfig() {
		register(RestMicroservice.class);
	}
}
