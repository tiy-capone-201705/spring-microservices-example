package com.theironyard.example.microservices.services;

import java.util.List;
import java.util.UUID;

import com.theironyard.example.microservices.models.Task;
import com.theironyard.example.microservices.models.TaskType;

public interface TaskService {
	Task create(String name, Integer amount, String description, TaskType type);
	void save(Task task);
	Task getById(Integer id);
	Task getByUuid(UUID uuid);
	List<Task> all();
}