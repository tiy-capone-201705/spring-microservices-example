package com.theironyard.example.microservices.services;

import javax.persistence.EntityManagerFactory;

import org.springframework.stereotype.Service;

@Service
public class TaskDaoFactoryImpl implements TaskDaoFactory {
	private EntityManagerFactory factory;
	
	public TaskDaoFactoryImpl(EntityManagerFactory factory) {
		this.factory = factory;
	}
	
	@Override
	public TaskDao create() {
		return new TaskDaoImpl(factory);
	}
}
