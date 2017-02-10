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
	
	private Integer remoteId;
	private Boolean done;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	
	public Task() {
		created = new Date();
		done = false;
	}
	
	public Task(Integer remoteId) {
		this();
		this.remoteId = remoteId;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getRemoteId() {
		return remoteId;
	}
	public void setRemoteId(Integer remoteId) {
		this.remoteId = remoteId;
	}
	public Boolean getDone() {
		return done != null && done;
	}
	public void setDone(Boolean done) {
		this.done = done;
	}
}
