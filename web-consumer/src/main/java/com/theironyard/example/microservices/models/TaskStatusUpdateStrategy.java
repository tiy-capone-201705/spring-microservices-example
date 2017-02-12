package com.theironyard.example.microservices.models;

public interface TaskStatusUpdateStrategy {
	void getUpdateFor(Task task);
}
