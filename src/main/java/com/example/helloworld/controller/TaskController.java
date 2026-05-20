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
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
		CreateTaskRequest createTaskRequest = taskMapper.fromDto(createTaskRequestDto);
		Task task = taskService.createTask(createTaskRequest);
		TaskDto createdTaskDto = taskMapper.toDto(task);

		return new ResponseEntity<>(createdTaskDto, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<TaskDto>> listTasks(){
		List<Task> tasks = taskService.listTasks();
		List<TaskDto> taskDtos = tasks.stream().map(taskMapper::toDto).toList();
		return ResponseEntity.ok(taskDtos);
	}
}

