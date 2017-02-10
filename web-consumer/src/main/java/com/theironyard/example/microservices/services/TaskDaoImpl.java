package com.theironyard.example.microservices.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.stereotype.Service;

import com.theironyard.example.microservices.models.Task;

@Service
class TaskDaoImpl implements TaskDao {
	private EntityManagerFactory factory;
	
	public TaskDaoImpl(EntityManagerFactory factory) {
		this.factory = factory;
	}
	
	@Override
	public Task save(Task task) {
		EntityManager manager = factory.createEntityManager();
		manager.getTransaction().begin();
		task = manager.merge(task);
		manager.getTransaction().commit();
		return task;
	}

	@Override
	public List<Task> all() {
		EntityManager manager = factory.createEntityManager();
		return manager.createQuery("from Task", Task.class).getResultList();
	}
}
