package com.theironyard.example.microservices.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class TaskDetail {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	
	@Column(nullable=false)
	private TaskStatus status;
	
	@ManyToOne(fetch=FetchType.EAGER, optional=false)
	private Task task;
	
	private String description;
	
	public TaskDetail() {
		this.created = new Date();
		this.status = TaskStatus.CREATED; 
	}

	public TaskDetail(Task task, TaskStatus status, String description) {
		this();
		this.status = status;
		this.task = task;
		this.description = description;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TaskStatus getStatus() {
		return status;
	}

	public void setStatus(TaskStatus status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
