package com.example.helloworld.service.impl;

import com.example.helloworld.domain.CreateTaskRequest;
import com.example.helloworld.domain.UpdateTaskRequest;
import com.example.helloworld.domain.entity.Task;
import com.example.helloworld.domain.entity.TaskStatus;
import com.example.helloworld.domain.entity.UserAccount;
import com.example.helloworld.exception.UserAccountNotFoundException;
import com.example.helloworld.exception.TaskNotFoundException;
import com.example.helloworld.repository.TaskRepository;
import com.example.helloworld.repository.UserAccountRepository;
import com.example.helloworld.service.TaskService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

	private final TaskRepository taskRepository;
	private final UserAccountRepository userAccountRepository;

	public TaskServiceImpl(TaskRepository taskRepository, UserAccountRepository userAccountRepository) {
		this.taskRepository = taskRepository;
		this.userAccountRepository = userAccountRepository;
	}

	@Override
	public Task createTask(CreateTaskRequest request) {
		Instant now = Instant.now();
		UserAccount userAccount = userAccountRepository.findById(request.userAccountId())
			.orElseThrow(() -> new UserAccountNotFoundException(request.userAccountId()));

		Task task = new Task(
				null,
				request.title(),
				request.description(),
				request.dueDate(),
				TaskStatus.OPEN,
				now,
				now
		);
		task.setUserAccount(userAccount);

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

	@Transactional
	@Override
	public void deleteTask(UUID taskId) {
		taskRepository.deleteById(taskId);
	}
}
