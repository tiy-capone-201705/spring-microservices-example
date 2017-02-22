package com.theironyard.example.microservices.services;

import java.util.List;
import java.util.UUID;

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
	
	public Task handleTaskAsync(UUID remoteId) {
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
	
	public void completeTask(Integer id) {
		EntityManager manager = factory.createEntityManager();
		manager.getTransaction().begin();
		Task task = manager.find(Task.class, id);
		task.setDone(true);
		manager.getTransaction().commit();
		manager.close();
	}

	@Override
	public List<Task> getCompletedTasks() {
		EntityManager manager = factory.createEntityManager();
		return manager
				.createQuery("from Task where done = true", Task.class)
				.getResultList();
	}

	@Override
	public List<Task> getIncompleteTasks() {
		EntityManager manager = factory.createEntityManager();
		return manager
				.createQuery("from Task where done = false", Task.class)
				.getResultList();
	}
}
