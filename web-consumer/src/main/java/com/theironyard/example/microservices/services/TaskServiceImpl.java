package com.theironyard.example.microservices.services;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.Future;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Service;

import com.theironyard.example.microservices.models.ImmediateTask;
import com.theironyard.example.microservices.models.RestTaskStatusUpdateStrategyImpl;
import com.theironyard.example.microservices.models.RestfulTask;
import com.theironyard.example.microservices.models.Task;
import com.theironyard.example.microservices.models.TaskStatus;
import com.theironyard.example.microservices.models.TaskType;

@Service
public class TaskServiceImpl implements TaskService {
	private Log log;
	private TaskDaoFactory factory;
	private ServicesConfigurationService servicesConfig;
	
	public TaskServiceImpl(TaskDaoFactory factory, ServicesConfigurationService servicesConfig) {
		this.factory = factory;
		this.servicesConfig = servicesConfig;
		log = LogFactory.getLog(TaskServiceImpl.class);
	}
	
	public void save(Task task) {
		TaskDao dao = factory.create();
		dao.save(task);
	}
	
	public Task create(String name, Integer amount, String description, TaskType type) {
		TaskDao dao = factory.create();
		Task newTask = null;
		switch (type) {
		case BOTH:
			break;
		case EVENTED:
			break;
		case NONE:
			newTask = new ImmediateTask(name, description, amount);
			break;
		case RESTFUL:
			newTask = new RestfulTask(name, description, amount);
			break;
		default:
			break;
		}
		Task savedTask = dao.save(newTask);
		
		if (type.equals(TaskType.BOTH) || type.equals(TaskType.RESTFUL)) {
			while (true) {
				try {
					postToRestfulService(dao, savedTask);
					break;
				} catch (InterruptedException ie) {}
			}
		}
		
		if (type.equals(TaskType.NONE)) {
			savedTask.setStatus(TaskStatus.COMPLETED, null);
			dao.update(savedTask);
		}
		
		return savedTask;
	}
	
	public List<Task> all() {
		return factory.create().all();
	}
	
	public Task getById(Integer id) {
		return factory.create().getById(id);
	}
	
	public Task getByUuid(UUID uuid) {
		return factory.create().getByUuid(uuid);
	}
	
	@Async
	public Future<Integer> postToRestfulService(TaskDao dao, Task task) throws InterruptedException {
		if (!(task instanceof RestfulTask)) {
			return new AsyncResult<Integer>(-1);
		}
		RestfulTask restfulTask = (RestfulTask) task;
		RestTemplate template = new RestTemplate();
		try {
			String url = servicesConfig.restCreationPath();
			log.info("RESTful creation path: " + url);
			String restStatusUrl = template.postForObject(url, new RestTask(restfulTask.getUuid()), String.class, new HttpHeaders());
			restfulTask.setRestStatusUrl(servicesConfig.restAbsoluteFromRelative(restStatusUrl));
			restfulTask.setStatus(TaskStatus.IN_PROGRESS, restStatusUrl);
		} catch (Exception e) {
			log.error("Failed to post to restful service", e);
			restfulTask.setStatus(TaskStatus.ERROR, e.getMessage());
		}
		dao.update(restfulTask);
		return new AsyncResult<Integer>(restfulTask.getId());
	}
	
	public class RestTask {
		private UUID remoteId;
		
		public RestTask(UUID remoteId) {
			this.remoteId = remoteId;
		}

		public UUID getRemoteId() {
			return remoteId;
		}

		public void setRemoteId(UUID remoteId) {
			this.remoteId = remoteId;
		}
	}
}
