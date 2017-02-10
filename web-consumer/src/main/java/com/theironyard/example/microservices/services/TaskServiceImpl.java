package com.theironyard.example.microservices.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Service;

import com.theironyard.example.microservices.models.Task;
import com.theironyard.example.microservices.models.TaskType;

@Service
public class TaskServiceImpl implements TaskService {
	@Value("${services.rest}")
	private String url;
	
	private TaskDaoFactory factory;
	
	public TaskServiceImpl(TaskDaoFactory factory) {
		this.factory = factory;
	}
	
	public Task create(String name, Integer amount, String description, TaskType type) {
		RestTemplate template = new RestTemplate();
		TaskDao dao = factory.create();
		dao.beginTransaction();
		Task task = dao.save(new Task(description, name, amount, type));
		
		if (type.equals(TaskType.BOTH) || type.equals(TaskType.RESTFUL)) {
			String restStatusUrl = template.postForObject(url, new RestTask(task.getId()), String.class, new HttpHeaders());
			task.setRestStatusUrl(restStatusUrl);
			dao.update(task);
		}
		dao.commitTransaction();
		
		return task;
	}
	
	public List<Task> all() {
		return factory.create().all();
	}
	
	public class RestTask {
		private Integer remoteId;
		
		public RestTask(Integer remoteId) {
			this.remoteId = remoteId;
		}

		public Integer getRemoteId() {
			return remoteId;
		}

		public void setRemoteId(Integer remoteId) {
			this.remoteId = remoteId;
		}
	}
}
