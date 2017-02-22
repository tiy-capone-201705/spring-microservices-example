package com.theironyard.example.microservices.services;

import java.util.List;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.theironyard.example.microservices.models.Task;
import com.theironyard.example.microservices.models.TaskStatusUpdateStrategy;

@Service
public class TaskUpdaterServiceImpl implements TaskUpdaterService {
	private TaskService service;
	private List<TaskStatusUpdateStrategy> strategies;
	
	public TaskUpdaterServiceImpl(TaskService service, List<TaskStatusUpdateStrategy> strategies) {
		this.service = service;
		this.strategies = strategies;
	}

	@Override
	@Async
	public void run() {
		try {
			while (true) {
				Thread.sleep(1000);
				
				List<Task> tasks = service.all();
				for (Task task : tasks) {
					if (task.isComplete()) {
						continue;
					}
					for (TaskStatusUpdateStrategy strategy : strategies) {
						task.accept(strategy);
						service.save(task);
					}
				}
			}
		} catch (Exception e) {}
	}
}
