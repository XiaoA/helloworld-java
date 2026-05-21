package com.example.helloworld.mapper.impl;

import com.example.helloworld.domain.CreateTaskRequest;
import com.example.helloworld.domain.UpdateTaskRequest;
import com.example.helloworld.domain.dto.CreateTaskRequestDto;
import com.example.helloworld.domain.dto.TaskDto;
import com.example.helloworld.domain.dto.UpdateTaskRequestDto;
import com.example.helloworld.domain.entity.Task;
import com.example.helloworld.mapper.TaskMapper;
import org.springframework.stereotype.Component;

@Component
public class TaskMapperImpl implements TaskMapper {

	@Override
	public CreateTaskRequest fromDto(CreateTaskRequestDto dto) {
		return new CreateTaskRequest(
			dto.userAccountId(),
			dto.title(),
			dto.description(),
			dto.dueDate()
		);
	}

	@Override
	public UpdateTaskRequest fromDto(UpdateTaskRequestDto dto) {
		return new UpdateTaskRequest(
			dto.title(),
			dto.description(),
			dto.dueDate(),
			dto.status()
		);
	}

	@Override
	public TaskDto toDto(Task task) {
		return new TaskDto(
			task.getId(),
			task.getTitle(),
			task.getDescription(),
			task.getDueDate(),
			task.getStatus()
		);
	}
}
