package com.theironyard.example.microservices.services;

import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.zeromq.ZMQ;

@Service
public class TaskChangedPublisher {
	private LinkedBlockingQueue<UUID> queue;
	private Log log;

	public TaskChangedPublisher() {
		queue = new LinkedBlockingQueue<UUID>();
		log = LogFactory.getLog(TaskChangedPublisher.class);
	}
	
	public void notifyOfUpdate(UUID uuid) {
		log.info("Queueing remoteId: " + uuid);
		queue.add(uuid);
	}
	
	@Async
	public void run() {
		log.info("Starting task changed publisher.");
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket publisher = context.socket(ZMQ.PUSH);
        publisher.bind("tcp://localhost:5557");

        try {
	        while (!Thread.currentThread().isInterrupted()) {
	        	log.info("Waiting for something to publish.");
	        	UUID uuid = queue.poll(1, TimeUnit.DAYS);
	        	log.info("Publishing: " + uuid.toString());
	        	publisher.send(uuid.toString(), 0);
	        }
        } catch (InterruptedException ie) {
        	log.error("publisher dying", ie);
        }

        publisher.close();
        context.term();
	}
}
