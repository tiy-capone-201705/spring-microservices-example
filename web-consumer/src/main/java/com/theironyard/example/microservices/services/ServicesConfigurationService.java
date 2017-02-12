package com.theironyard.example.microservices.services;

public interface ServicesConfigurationService {
	String restCreationPath();
	String restAbsoluteFromRelative(String path);
}
