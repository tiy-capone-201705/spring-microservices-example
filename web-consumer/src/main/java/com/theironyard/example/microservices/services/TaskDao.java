package com.theironyard.example.microservices.services;

import java.util.List;

import com.theironyard.example.microservices.models.Task;

interface TaskDao {
	Task save(Task task);
	void update(Task task);
	List<Task> all();
	Task getById(Integer id);
}