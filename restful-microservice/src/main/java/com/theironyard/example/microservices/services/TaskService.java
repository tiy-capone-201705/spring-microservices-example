package com.theironyard.example.microservices.services;

import com.theironyard.example.microservices.models.Task;

public interface TaskService {
	Task handleTaskAsync(Integer remoteId);
	
	Task getById(Integer id);
}
