package com.theironyard.example.microservices.services;

import java.util.concurrent.Future;

public interface TaskUpdaterService {
	Future<Boolean> run();
}
