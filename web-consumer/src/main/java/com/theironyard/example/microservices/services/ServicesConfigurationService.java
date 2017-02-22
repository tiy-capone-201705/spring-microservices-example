package com.theironyard.example.microservices.services;

public interface ServicesConfigurationService {
	String restCreationPath();
	String restUnapprovedPath();
	String restAbsoluteFromRelative(String path);
	String restAbsoluteApprovalUrl(Integer id);
	
	String mqNewTaskTopic();
	String mqUpdatedTaskTopic();
}
