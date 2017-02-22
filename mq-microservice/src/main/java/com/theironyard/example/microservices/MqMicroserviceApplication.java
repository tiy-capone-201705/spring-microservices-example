package com.theironyard.example.microservices;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

import com.theironyard.example.microservices.services.TaskChangedPublisher;
import com.theironyard.example.microservices.services.TaskReceiverService;

@EnableAsync
@SpringBootApplication
public class MqMicroserviceApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(MqMicroserviceApplication.class, args);

		TaskChangedPublisher publisher = (TaskChangedPublisher) context.getBean(TaskChangedPublisher.class);
		publisher.run();

		TaskReceiverService service = (TaskReceiverService) context.getBean(TaskReceiverService.class);
		service.run();
	}
}
