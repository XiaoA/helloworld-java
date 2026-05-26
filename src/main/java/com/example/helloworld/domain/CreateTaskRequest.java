package com.example.helloworld.domain;

import java.time.LocalDate;
import java.util.UUID;

public record CreateTaskRequest(
	UUID userAccountId,
	String title,
	String description,
	LocalDate dueDate
) {
}