package com.theironyard.example.microservices.models;

import javax.persistence.Column;

import javax.persistence.Entity;

@Entity
public class RestfulTask extends Task {
	@Column(nullable=false)
	private String restStatusUrl;
	
	public RestfulTask() {
		super();
	}
	
	public RestfulTask(String name, String description, Integer amount) {
		super(name, description, amount);
		restStatusUrl = "";
	}

	public void restComplete() {
		setStatus(TaskStatus.COMPLETED, "");
	}
	
	public String getRestStatusUrl() {
		return restStatusUrl;
	}

	public void setRestStatusUrl(String restStatusUrl) {
		this.restStatusUrl = restStatusUrl;
	}

	@Override
	public void accept(TaskStatusUpdateStrategy strategy) {
		strategy.visit(this);
	}
}
