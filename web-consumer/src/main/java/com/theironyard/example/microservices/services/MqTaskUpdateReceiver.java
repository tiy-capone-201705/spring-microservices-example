package com.theironyard.example.microservices.services;

import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.zeromq.ZMQ;

import com.theironyard.example.microservices.models.MqTask;

@Service
public class MqTaskUpdateReceiver {
	private ServicesConfigurationService config;
	private TaskService service;
	private Log log;
	
	public MqTaskUpdateReceiver(TaskService service, ServicesConfigurationService config) {
		this.service = service;
		this.config = config;
		log = LogFactory.getLog(MqTaskUpdateReceiver.class);
	}
	
	@Async
	public void run() {
		log.info("Starting the evented task update receiver.");
		ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket subscriber = context.socket(ZMQ.PULL);
        subscriber.connect(config.mqUpdatedTaskTopic());
        
        try {
	        while (true) {
	        	log.info("Listening for evented task updates");
	        	String uuid = subscriber.recvStr(0).trim();
	        	log.info("Received update for: " + uuid);
	        	MqTask task = (MqTask) service.getByUuid(UUID.fromString(uuid));
	        	log.info("Updating task: " + task);
	        	task.mqComplete();
	        	service.save(task);
	        }
        } catch (Exception e) {}
	}
}
