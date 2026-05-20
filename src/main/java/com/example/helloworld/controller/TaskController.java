package com.example.helloworld.controller;

import com.example.helloworld.domain.CreateTaskRequest;
import com.example.helloworld.domain.dto.CreateTaskRequestDto;
import com.example.helloworld.domain.dto.TaskDto;
import com.example.helloworld.domain.entity.Task;
import com.example.helloworld.mapper.TaskMapper;
import com.example.helloworld.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/tasks")
public class TaskController {

	private final TaskService taskService;
	private final TaskMapper taskMapper;

	public TaskController(TaskService taskService, TaskMapper taskMapper) {
		this.taskService = taskService;
		this.taskMapper = taskMapper;
	}

	@PostMapping
	public ResponseEntity<TaskDto> createTask(
		@Valid @RequestBody CreateTaskRequestDto createTaskRequestDto
	) {
		CreateTaskRequest createTaskRequest = taskMapper.fromDTO(createTaskRequestDto);
		Task task = taskService.createTask(createTaskRequest);
		TaskDto createdTaskDto = taskMapper.toDTO(task);

		return new ResponseEntity<>(createdTaskDto, HttpStatus.CREATED);
	}
}
