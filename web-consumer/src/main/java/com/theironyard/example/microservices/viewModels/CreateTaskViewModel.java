package com.theironyard.example.microservices.viewModels;

import com.theironyard.example.microservices.models.TaskType;

public class CreateTaskViewModel {
	private String name;
	private Integer amount;
	private String description;
	private TaskType type;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public TaskType getType() {
		return type;
	}
	public void setType(TaskType type) {
		this.type = type;
	}
}
