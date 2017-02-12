package com.theironyard.example.microservices.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
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
	private String restStatusUrl;
	private Boolean restComplete;
	private Boolean mqComplete;
	
	@Column(nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	
	@Column(nullable=false)
	private TaskType type;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="task")
	@OrderBy("created DESC")
	private List<TaskDetail> details;
	
	public Task() {
		created = new Date();
		details = new ArrayList<TaskDetail>();
	}
	
	public Task(String description, String name, Integer amount, TaskType type) {
		this();
		this.description = description;
		this.name = name;
		this.amount = amount;
		this.type = type;
		this.details.add(new TaskDetail(this, TaskStatus.CREATED, null));
	}
	
	public Boolean dependsOn(TaskType type) {
		return this.type.equals(TaskType.BOTH) || this.type.equals(type);
	}
	
	public Boolean isComplete() {
		TaskStatus status = getStatus();
		return status.equals(TaskStatus.COMPLETED) || status.equals(TaskStatus.ERROR);
	}
	
	public void restComplete() {
		restComplete = true;
		calculateStatus();
	}
	
	public void calculateStatus() {
		if (isComplete()) {
			return;
		}
		if (type.equals(TaskType.BOTH) && restComplete && mqComplete) {
			setStatus(TaskStatus.COMPLETED, "");
		} else if (type.equals(TaskType.RESTFUL) && restComplete) {
			setStatus(TaskStatus.COMPLETED, "");
		} else if (type.equals(TaskType.EVENTED) && mqComplete) {
			setStatus(TaskStatus.COMPLETED, "");
		}
	}
	
	@Override
	public String toString() {
		return String.format("Task<%d %s %s>", id, type, getStatus());
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
	
	public TaskStatus getStatus() {
		return details.get(0).getStatus();
	}
	
	public void setStatus(TaskStatus status, String description) {
		details.add(new TaskDetail(this, status, description));
	}
}
