package com.example.helloworld.service.impl;

import com.example.helloworld.domain.CreateTaskRequest;
import com.example.helloworld.domain.UpdateTaskRequest;
import com.example.helloworld.domain.entity.Task;
import com.example.helloworld.domain.entity.TaskStatus;
import com.example.helloworld.exception.TaskNotFoundException;
import com.example.helloworld.repository.TaskRepository;
import com.example.helloworld.service.TaskService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

	private final TaskRepository taskRepository;

	public TaskServiceImpl(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}

	@Override
	public Task createTask(CreateTaskRequest request) {
		Instant now = Instant.now();

		Task task = new Task(
				null,
				request.title(),
				request.description(),
				request.dueDate(),
				TaskStatus.OPEN,
				now,
				now
		);

		return taskRepository.save(task);
	}

	@Override
	public List<Task> listTasks() {
		return taskRepository.findAll(Sort.by(Sort.Direction.ASC, "created"));
	}

	@Override
	public Task updateTask(UUID taskId, UpdateTaskRequest request) {
		Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));

		task.setTitle(request.title());
		task.setDescription(request.description());
		task.setDueDate(request.dueDate());
		task.setStatus(request.status());
		task.setUpdated(Instant.now());

		return taskRepository.save(task);
	}
}
