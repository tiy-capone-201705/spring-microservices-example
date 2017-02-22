package com.theironyard.example.microservices.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class Task {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private UUID uuid;
	
	@Column(nullable=false)
	private String name;

	@Column(nullable=false)
	private Integer amount;

	@Column(nullable=false)
	private String description;
	
	@Column(nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="task")
	@OrderBy("created DESC")
	private List<TaskDetail> details;
	
	public Task() {
		created = new Date();
		details = new ArrayList<TaskDetail>();
		uuid = UUID.randomUUID();
	}
	
	public Task(String description, String name, Integer amount) {
		this();
		this.description = description;
		this.name = name;
		this.amount = amount;
		this.details.add(new TaskDetail(this, TaskStatus.CREATED, null));
	}
	
	public Boolean isComplete() {
		TaskStatus status = getStatus();
		return status.equals(TaskStatus.COMPLETED) || status.equals(TaskStatus.ERROR);
	}
	
	public abstract void accept(TaskStatusUpdateStrategy strategy);
	
	@Override
	public String toString() {
		return String.format("Task<%d %s %s>", id, this.getClass().getSimpleName(), getStatus());
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
		return details.get(0).getStatus();
	}
	
	public void setStatus(TaskStatus status, String description) {
		details.add(new TaskDetail(this, status, description));
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
}
