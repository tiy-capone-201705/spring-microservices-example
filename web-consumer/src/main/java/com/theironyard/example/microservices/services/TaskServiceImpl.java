package com.theironyard.example.microservices.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.theironyard.example.microservices.models.Task;
import com.theironyard.example.microservices.models.TaskType;

@Service
public class TaskServiceImpl implements TaskService {
	private TaskDao dao;
	
	public TaskServiceImpl(TaskDao dao) {
		this.dao = dao;
	}
	
	public Task create(String name, Integer amount, String description, TaskType type) {
		return dao.save(new Task(description, name, amount, type));
	}
	
	public List<Task> all() {
		return dao.all();
	}
}
