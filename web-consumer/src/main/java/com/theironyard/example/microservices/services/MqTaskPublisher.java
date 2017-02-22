package com.theironyard.example.microservices.services;

import java.util.concurrent.LinkedBlockingQueue;

import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.zeromq.ZMQ;

import com.theironyard.example.microservices.services.TaskDao;
import com.theironyard.example.microservices.models.MqTask;

@Service
public class MqTaskPublisher {
	private ServicesConfigurationService config;
	private LinkedBlockingQueue<MqTask> queue;
	private TaskDao dao;
	private Log log;

	public MqTaskPublisher(ServicesConfigurationService config, TaskDao dao) {
		this.config = config;
		this.dao = dao;
		queue = new LinkedBlockingQueue<>();
		log = LogFactory.getLog(MqTaskPublisher.class);
	}
	
	public void publishNewTask(MqTask task) {
		queue.add(task);
	}
	
	@Async
	public void run() {
		while (true) {
			log.info("Creating publisher to: " + config.mqNewTaskTopic());
	        ZMQ.Context context = ZMQ.context(1);

	        ZMQ.Socket publisher = context.socket(ZMQ.PUSH);
	        publisher.bind(config.mqNewTaskTopic());

	        try {
		        while (!Thread.currentThread().isInterrupted()) {
		        	log.info("Waiting for something to publish.");
		        	MqTask task = queue.poll(1, TimeUnit.DAYS);
		        	
		        	log.info("Sending new task: " + task);
		        	publisher.send(task.getUuid() + ":" + task.getAmount(), 0);
		        	
		        	task.mqSent();
		        	dao.save(task);
		        }
	        } catch (InterruptedException ie) {}

	        log.info("Closing the publisher and context.");
	        publisher.close();
	        context.term();
		}
	}
}
