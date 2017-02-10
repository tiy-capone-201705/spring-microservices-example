package com.theironyard.example.microservices.services;

import java.util.List;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Service;

import com.theironyard.example.microservices.models.Task;
import com.theironyard.example.microservices.models.TaskStatus;
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
		TaskDao dao = factory.create();
		dao.beginTransaction();
		Task task = dao.save(new Task(description, name, amount, type));
		
		if (type.equals(TaskType.BOTH) || type.equals(TaskType.RESTFUL)) {
			while (true) {
				try {
					PostToRestfulService(dao, task);
					break;
				} catch (InterruptedException ie) {}
			}
		}
		
		if (type.equals(TaskType.NONE)) {
			task.setStatus(TaskStatus.COMPLETED);
			dao.commitTransaction();
		}
		
		return task;
	}
	
	public List<Task> all() {
		return factory.create().all();
	}
	
	@Async
	public Future<Integer> PostToRestfulService(TaskDao dao, Task task) throws InterruptedException {
		RestTemplate template = new RestTemplate();
		String restStatusUrl = template.postForObject(url, new RestTask(task.getId()), String.class, new HttpHeaders());
		task.setRestStatusUrl(restStatusUrl);
		task.setStatus(TaskStatus.IN_PROGRESS);
		dao.update(task);
		dao.commitTransaction();
		return new AsyncResult<Integer>(task.getId());
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
