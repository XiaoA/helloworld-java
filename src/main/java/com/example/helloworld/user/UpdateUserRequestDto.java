package com.example.helloworld.user;

import com.example.helloworld.account.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record UpdateUserRequestDto(

	@NotBlank(message = ERROR_MESSAGE_DISPLAY_NAME_REQUIRED)
	@Length(max = 50, message = ERROR_MESSAGE_DISPLAY_NAME_LENGTH)
	String displayName,

	@NotBlank(message = ERROR_MESSAGE_EMAIL_REQUIRED)
	String email,

	@NotNull(message = ERROR_MESSAGE_USER_ROLE_REQUIRED)
	UserRole role
) {
	private static final String ERROR_MESSAGE_DISPLAY_NAME_REQUIRED = "You must enter a name";
	private static final String ERROR_MESSAGE_DISPLAY_NAME_LENGTH = "Your name must be between 1 and 50 characters";
	private static final String ERROR_MESSAGE_USER_ROLE_REQUIRED = "You must choose a user role";
	private static final String ERROR_MESSAGE_EMAIL_REQUIRED = "You must provide a valid email";
}

