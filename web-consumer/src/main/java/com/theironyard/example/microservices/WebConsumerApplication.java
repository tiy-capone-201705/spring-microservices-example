package com.theironyard.example.microservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

import com.theironyard.example.microservices.services.MqTaskPublisher;
import com.theironyard.example.microservices.services.MqTaskUpdateReceiver;
import com.theironyard.example.microservices.services.TaskUpdaterService;

@SpringBootApplication
@EnableAsync
public class WebConsumerApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(WebConsumerApplication.class, args);
		TaskUpdaterService service = (TaskUpdaterService) context.getBean(TaskUpdaterService.class);
		service.run();

		MqTaskPublisher publisher = (MqTaskPublisher) context.getBean(MqTaskPublisher.class);
		publisher.run();

		MqTaskUpdateReceiver receiver = (MqTaskUpdateReceiver) context.getBean(MqTaskUpdateReceiver.class);
		receiver.run();
	}
}
