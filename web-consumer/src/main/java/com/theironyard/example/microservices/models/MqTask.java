package com.theironyard.example.microservices.models;

import javax.persistence.Entity;

@Entity
public class MqTask extends Task {
	public MqTask() {
		super();
	}
	
	public MqTask(String name, String description, Integer amount) {
		super(name, description, amount);
	}

	@Override
	public void accept(TaskStatusUpdateStrategy strategy) {
		strategy.visit(this);
	}
	
	public void mqSent() {
		setStatus(TaskStatus.IN_PROGRESS, "");
	}

	public void mqComplete() {
		setStatus(TaskStatus.COMPLETED, "");
	}
}
