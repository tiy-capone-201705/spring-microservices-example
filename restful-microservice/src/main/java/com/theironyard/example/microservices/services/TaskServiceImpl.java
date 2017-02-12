package com.theironyard.example.microservices.services;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.stereotype.Service;

import com.theironyard.example.microservices.models.Task;

@Service
public class TaskServiceImpl implements TaskService {
	private EntityManagerFactory factory;
	
	public TaskServiceImpl(EntityManagerFactory factory) {
		this.factory = factory;
	}
	
	public Task handleTaskAsync(Integer remoteId) {
		EntityManager manager = factory.createEntityManager();
		manager.getTransaction().begin();
		Task task = manager.merge(new Task(remoteId));
		manager.getTransaction().commit();
		manager.close();
		return task;
	}
	
	public Task getById(Integer id) {
		EntityManager manager = factory.createEntityManager();
		Task task = manager.find(Task.class, id);
		manager.close();
		return task;
	}
}
