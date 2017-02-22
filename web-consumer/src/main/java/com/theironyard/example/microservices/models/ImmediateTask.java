package com.theironyard.example.microservices.models;

import javax.persistence.Entity;

@Entity
public class ImmediateTask extends Task {
	public ImmediateTask() {
		super();
	}
	
	public ImmediateTask(String description, String name, Integer amount) {
		super(description, name, amount);
	}

	@Override
	public void accept(TaskStatusUpdateStrategy strategy) {
		strategy.visit(this);
	}
}
