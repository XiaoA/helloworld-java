package com.example.helloworld.mapper;

import com.example.helloworld.domain.CreateTaskRequest;
import com.example.helloworld.domain.UpdateTaskRequest;
import com.example.helloworld.domain.dto.CreateTaskRequestDto;
import com.example.helloworld.domain.dto.TaskDto;
import com.example.helloworld.domain.dto.UpdateTaskRequestDto;
import com.example.helloworld.domain.entity.Task;

public interface TaskMapper {

	CreateTaskRequest fromDto(CreateTaskRequestDto dto);
	UpdateTaskRequest fromDto(UpdateTaskRequestDto dto);
	TaskDto toDto(Task task);
}
