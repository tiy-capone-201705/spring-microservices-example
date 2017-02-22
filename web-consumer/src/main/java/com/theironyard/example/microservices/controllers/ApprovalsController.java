package com.theironyard.example.microservices.controllers;

import java.util.ArrayList;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.theironyard.example.microservices.models.Task;
import com.theironyard.example.microservices.models.TaskApprovalNeed;
import com.theironyard.example.microservices.services.ServicesConfigurationService;
import com.theironyard.example.microservices.services.TaskService;

@Controller
@RequestMapping("/approvals")
public class ApprovalsController {
	private ServicesConfigurationService config;
	private TaskService service;
	
	public ApprovalsController(ServicesConfigurationService config, TaskService service) {
		this.config = config;
		this.service = service;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String dashboard(Model model) {
		return "approvals/index";
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/unapproved-tasks")
	public @ResponseBody List<TaskApprovalNeed> getUnapprovedTasks() throws InterruptedException, ExecutionException {
		Future<List<TaskApprovalNeed>> restfuls = getRestfulApprovals();
		
		while (!restfuls.isDone()) {
			Thread.sleep(10);
		}
		
		return restfuls.get();
	}
	
	@Async
	private Future<List<TaskApprovalNeed>> getRestfulApprovals() {
		String url = config.restUnapprovedPath();
		RestTemplate template = new RestTemplate();
		List<TaskApprovalNeed> needs = new ArrayList<TaskApprovalNeed>();

		ResponseEntity<List<TaskApprovalNeed>> body = template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<TaskApprovalNeed>>() {});
		needs = body.getBody();
		
		for (TaskApprovalNeed need : needs) {
			Task task = service.getByUuid(need.getRemoteId());
			if (task != null) {
				need.setName(task.getName());
				need.setApprovalUrl(config.restAbsoluteApprovalUrl(need.getId()));
			}
		}

		return new AsyncResult<List<TaskApprovalNeed>>(needs);
	}
}
