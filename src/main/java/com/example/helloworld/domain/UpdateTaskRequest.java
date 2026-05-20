package com.example.helloworld.domain;

import com.example.helloworld.domain.entity.TaskStatus;

import java.time.LocalDate;

public record UpdateTaskRequest(
	String title,
	String description,
	LocalDate dueDate,
	TaskStatus status
) {
}
