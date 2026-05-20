package com.example.helloworld.domain.dto;

import com.example.helloworld.domain.entity.TaskStatus;

import java.time.LocalDate;
import java.util.UUID;

public record TaskDto(
	UUID id,
	String title,
	String description,
	LocalDate dueDate,
	TaskStatus status
) {

}
