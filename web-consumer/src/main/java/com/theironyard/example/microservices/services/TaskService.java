package com.theironyard.example.microservices.services;

import java.util.List;

import com.theironyard.example.microservices.models.Task;
import com.theironyard.example.microservices.models.TaskType;

public interface TaskService {
	Task create(String name, Integer amount, String description, TaskType type);
	void save(Task task);
	Task getById(Integer id);
	List<Task> all();
}