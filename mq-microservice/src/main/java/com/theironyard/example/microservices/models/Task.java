package com.theironyard.example.microservices.models;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
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
	
	@Column(nullable=false)
	private Integer duration;
	
	@Column(nullable=false)
	private UUID remoteId;
	
	private Boolean done;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	
	public Task() {
		created = new Date();
		done = false;
	}
	
	public Task(UUID remoteId, Integer duration) {
		this();
		this.remoteId = remoteId;
		this.duration = duration;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public UUID getRemoteId() {
		return remoteId;
	}
	public void setRemoteId(UUID remoteId) {
		this.remoteId = remoteId;
	}
	
	public Boolean getDone() {
		return done != null && done;
	}
	public void setDone(Boolean done) {
		this.done = done;
	}

	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
}
