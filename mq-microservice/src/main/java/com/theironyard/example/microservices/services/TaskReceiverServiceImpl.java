package com.theironyard.example.microservices.services;

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.zeromq.ZMQ;

import com.theironyard.example.microservices.models.Task;

@Service
public class TaskReceiverServiceImpl implements TaskReceiverService {
	private EntityManagerFactory factory;
	private TaskStatusUpdater updater;
	private Log log;
	
	public TaskReceiverServiceImpl(EntityManagerFactory factory, TaskStatusUpdater updater) {
		this.factory = factory;
		log = LogFactory.getLog(TaskReceiverServiceImpl.class);
		this.updater = updater;
	}
	
	@Async
	@Override
	public void run() {
		log.info("Starting task receiver service.");
		ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket subscriber = context.socket(ZMQ.PULL);
        subscriber.connect("tcp://localhost:5556");
        
        log.info("Everything seems ok.");
        try {
	        while (true) {
	        	log.info("Waiting for something to receive.");
	        	String value = subscriber.recvStr(0).trim();
	        	log.info("Recevied remote id/amount: " + value);
	        	String[] values = value.split(":");
	        	String remoteId = values[0];
	        	String duration = values[1];
	        	EntityManager manager = factory.createEntityManager();
	        	manager.getTransaction().begin();
	        	Task task = manager.merge(new Task(UUID.fromString(remoteId), Integer.parseInt(duration)));
	        	manager.getTransaction().commit();
	        	updater.updateTask(task);
	        }
        } catch (Exception e) {
	    	log.error("New task subscriber failed.", e);
	    }
	}
}
