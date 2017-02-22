package com.theironyard.example.microservices.models;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestTaskStatusUpdateStrategyImpl implements TaskStatusUpdateStrategy {
	private Log log;
	
	public RestTaskStatusUpdateStrategyImpl() {
		log = LogFactory.getLog(RestTaskStatusUpdateStrategyImpl.class);
	}
	
	@Override
	public void visit(ImmediateTask task) {}
	
	@Override
	public void visit(MqTask task) {}
	
	@Override
	public void visit(RestfulTask task) {
		log.info("Checking " + task);
		String url = task.getRestStatusUrl();
		if (url.length() > 0) {
			RestTemplate template = new RestTemplate();
			try {
				Boolean complete = template.getForObject(url, Boolean.class, new HttpHeaders());
				log.info("Got response " + complete);
				if (complete) {
					task.restComplete();
				}
			} catch (Exception e) {
				log.error("Error in getting update for task.", e);
			}
		}
	}
}
