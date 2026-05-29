package com.example.helloworld.model.dto;

import com.example.helloworld.model.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateUserRequestDto(

	@NotBlank(message = ERROR_MESSAGE_DISPLAY_NAME_REQUIRED)
	String displayName,

	@NotBlank(message = ERROR_MESSAGE_EMAIL_REQUIRED)
	String email,

	@NotNull(message = ERROR_MESSAGE_ROLE_REQUIRED)
	UserRole role
) {
	public static final String ERROR_MESSAGE_DISPLAY_NAME_REQUIRED = "Display name is required";
	public static final String ERROR_MESSAGE_EMAIL_REQUIRED = "Email is required";
	public static final String ERROR_MESSAGE_ROLE_REQUIRED = "Role is required";
}
