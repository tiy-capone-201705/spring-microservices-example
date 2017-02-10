package com.theironyard.example.microservices.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.config.BeanDefinition;

import com.theironyard.example.microservices.models.Task;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
class TaskDaoImpl implements TaskDao {
	private EntityManagerFactory factory;
	private EntityManager manager;
	
	public TaskDaoImpl(EntityManagerFactory factory) {
		this.factory = factory;
	}
	
	@Override
	public Task save(Task task) {
		task = manager.merge(task);
		return task;
	}

	@Override
	public List<Task> all() {
		EntityManager manager = factory.createEntityManager();
		return manager.createQuery("from Task", Task.class).getResultList();
	}
	
	@Override
	public void update(Task task) {
		save(task);
	}
	
	@Override
	public void beginTransaction() {
		manager = factory.createEntityManager();
		manager.getTransaction().begin();
	}
	
	@Override
	public void commitTransaction() {
		manager.getTransaction().commit();
	}
}
