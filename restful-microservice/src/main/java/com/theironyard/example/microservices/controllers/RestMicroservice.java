package com.theironyard.example.microservices.controllers;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import com.theironyard.example.microservices.models.Task;
import com.theironyard.example.microservices.services.TaskService;

@Component
@Path("/tasks")
@Produces(MediaType.APPLICATION_JSON_VALUE)
public class RestMicroservice {
	private TaskService service;
	
	public RestMicroservice(TaskService service) {
		this.service = service;
	}
	
	@POST
	public Response create(Task task) {
		task = service.handleTaskAsync(task.getRemoteId());
		return Response.ok("/tasks/" + task.getId() + "/status").build();
	}
	
	@GET
	@Path("/{id}/status")
	public Boolean getStatus(@PathParam("id") Integer id) {
		return service.getById(id).getDone();
	}
}
