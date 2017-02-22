package com.theironyard.example.microservices.controllers;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.theironyard.example.microservices.models.Task;
import com.theironyard.example.microservices.services.TaskService;
import com.theironyard.example.microservices.viewModels.CreateTaskViewModel;

@Controller
@RequestMapping("/")
public class WorkController {
	private TaskService service;
	
	public WorkController(TaskService service) {
		this.service = service;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String dashboard(Model model) {
		return "work/index";
	}
	
	@RequestMapping("/tasks")
	public @ResponseBody List<Task> tasks() {
		return service.all();
	}
	
	@RequestMapping(value="/tasks", method=RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody CreateTaskViewModel viewModel) {
		Task task = service.create(viewModel.getName(), viewModel.getAmount(), viewModel.getDescription(), viewModel.getType());
		HttpHeaders headers = new HttpHeaders();
		headers.set("Location", "/tasks/" + task.getId());
		return new ResponseEntity<>(task, headers, HttpStatus.ACCEPTED);
	}

	@RequestMapping("/tasks/{id}")
	public @ResponseBody Task details(@PathVariable Integer id) {
		return service.getById(id);
	}
}
