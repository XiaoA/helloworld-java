package com.example.helloworld.mapper;

import com.example.helloworld.domain.CreateTaskRequest;
import com.example.helloworld.domain.dto.CreateTaskRequestDto;
import com.example.helloworld.domain.dto.TaskDto;
import com.example.helloworld.domain.entity.Task;

public interface TaskMapper {

	CreateTaskRequest fromDto(CreateTaskRequestDto dto);

	TaskDto toDto(Task task);
}
