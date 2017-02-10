package com.theironyard.example.microservices.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Task {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	private String name;
	private Integer amount;
	private String description;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	
	private TaskStatus status;
	private TaskType type;
	private String restStatusUrl;
	
	public Task() {
		this.created = new Date();
		this.status = TaskStatus.CREATED;
	}
	
	public Task(String description, String name, Integer amount, TaskType type) {
		this();
		this.description = description;
		this.name = name;
		this.amount = amount;
		this.type = type;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public TaskStatus getStatus() {
		return status;
	}

	public void setStatus(TaskStatus status) {
		this.status = status;
	}

	public TaskType getType() {
		return type;
	}

	public void setType(TaskType type) {
		this.type = type;
	}

	public String getRestStatusUrl() {
		return restStatusUrl;
	}

	public void setRestStatusUrl(String restStatusUrl) {
		this.restStatusUrl = restStatusUrl;
	}
}
