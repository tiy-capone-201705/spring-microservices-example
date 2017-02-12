package com.theironyard.example.microservices.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ServicesConfigurationServiceImpl implements ServicesConfigurationService {
	@Value("${services.rest.server}")
	private String restServer;
	
	@Value("${services.rest.createPath}")
	private String createPath;

	@Override
	public String restCreationPath() {
		return restServer + createPath;
	}

	@Override
	public String restAbsoluteFromRelative(String path) {
		return restServer + path;
	}
}
