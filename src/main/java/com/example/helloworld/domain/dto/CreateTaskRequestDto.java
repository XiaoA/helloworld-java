package com.example.helloworld.domain.dto;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record CreateTaskRequestDto(
	@NotNull(message = ERROR_MESSAGE_USER_ACCOUNT_ID_REQUIRED)
	UUID userAccountId,

	@NotBlank(message = ERROR_MESSAGE_TITLE_LENGTH)
	@Length(max = 255, message = ERROR_MESSAGE_TITLE_LENGTH)
	String title,

	@Length(max = 1000, message = ERROR_MESSAGE_DESCRIPTION_LENGTH)
	@Nullable
	String description,

	@FutureOrPresent(message = ERROR_MESSAGE_DUE_DATE_FUTURE)
	@Nullable
	LocalDate dueDate
) {

	private static final String ERROR_MESSAGE_USER_ACCOUNT_ID_REQUIRED = "User account id is required";
	private static final String ERROR_MESSAGE_TITLE_LENGTH = "Title length must be between 1 and 255 characters";
	private static final String ERROR_MESSAGE_DESCRIPTION_LENGTH = "Description length must be between 1 and 1000 characters";
	private static final String ERROR_MESSAGE_DUE_DATE_FUTURE = "Due date must be in the future";
}
