package com.theironyard.example.microservices.services;

import java.util.List;
import java.util.UUID;

import com.theironyard.example.microservices.models.Task;

public interface TaskService {
	Task handleTaskAsync(UUID remoteId);
	
	Task getById(Integer id);
	void completeTask(Integer id);

	List<Task> getCompletedTasks();

	List<Task> getIncompleteTasks();
}
