package com.theironyard.example.microservices.controllers;

import java.util.List;


import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

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
	
	@GET
	public List<Task> get(@QueryParam("done") Boolean isDone) {
		List<Task> tasks = null;
		if (isDone != null && isDone) {
			tasks = service.getCompletedTasks();
		} else {
			tasks = service.getIncompleteTasks();
		}
		return tasks;
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
	
	@PATCH
	@Path("/{id}")
	public Response patch(@PathParam("id") Integer id, @RequestBody List<PatchInstruction> instructions) {
		try {
			for (PatchInstruction instruction : instructions) {
				switch (instruction.getOp()) {
				case add:
					if (instruction.getPath().equals("/status") && instruction.getValue().equals("true")) {
						service.completeTask(id);
					}
					break;
				default:
					break;
				}
			}
			return Response.noContent().build();
		} catch (NullPointerException npe) {
			return Response.status(404).build();
		}
	}
}
