package com.theironyard.example.microservices.models;

public interface TaskStatusUpdateStrategy {
	void visit(ImmediateTask task);
	void visit(RestfulTask task);
}
