package com.theironyard.example.microservices.models;

import java.util.UUID;

public class TaskApprovalNeed {
	private Integer id;
	private String approvalUrl;
	private UUID remoteId;
	private String name;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getApprovalUrl() {
		return approvalUrl;
	}
	public void setApprovalUrl(String approvalUrl) {
		this.approvalUrl = approvalUrl;
	}
	
	public UUID getRemoteId() {
		return remoteId;
	}
	public void setRemoteId(UUID remoteId) {
		this.remoteId = remoteId;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
