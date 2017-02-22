package com.theironyard.example.microservices.services;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.theironyard.example.microservices.models.Task;

@Service
public class TaskStatusUpdater {
	private EntityManagerFactory factory;
	private Log log;
	private TaskChangedPublisher publisher;
	
	public TaskStatusUpdater(EntityManagerFactory factory, TaskChangedPublisher publisher) {
		this.factory = factory;
		log = LogFactory.getLog(TaskStatusUpdater.class);
		this.publisher = publisher;
	}
	
	@Async
	public void updateTask(Task task) throws InterruptedException {
		Thread.sleep(task.getDuration());
		EntityManager manager = factory.createEntityManager();
		manager.getTransaction().begin();
		task.setDone(true);
		manager.merge(task);
		manager.getTransaction().commit();
		log.info("Notifying of update on " + task.getRemoteId());
		publisher.notifyOfUpdate(task.getRemoteId());
	}
}
