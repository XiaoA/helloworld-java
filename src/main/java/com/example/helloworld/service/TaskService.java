package com.example.helloworld.service;

import com.example.helloworld.domain.CreateTaskRequest;
import com.example.helloworld.domain.entity.Task;

import java.util.List;

public interface TaskService {

	Task createTask(CreateTaskRequest request);

	List<Task> listTasks();
}
