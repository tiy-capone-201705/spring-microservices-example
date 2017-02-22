package com.theironyard.example.microservices.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ServicesConfigurationServiceImpl implements ServicesConfigurationService {
	@Value("${services.rest.server}")
	private String restServer;
	
	@Value("${services.rest.createPath}")
	private String restCreatePath;
	
	@Value("${services.rest.unapprovedListPath}")
	private String restUnapprovedListPath;

	@Override
	public String restCreationPath() {
		return restServer + restCreatePath;
	}
	
	@Override
	public String restUnapprovedPath() {
		return restServer + restUnapprovedListPath;
	}

	@Override
	public String restAbsoluteFromRelative(String path) {
		return restServer + path;
	}
	
	@Override
	public String restAbsoluteApprovalUrl(Integer id) {
		return restServer + "/tasks/" + id;
	}
}
