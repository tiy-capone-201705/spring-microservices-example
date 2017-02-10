package com.theironyard.example.microservices.services;

import org.springframework.stereotype.Component;

@Component
public interface TaskDaoFactory {
	TaskDao create();
}
