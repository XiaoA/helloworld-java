package com.example.helloworld.account;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record CreateAccountRequestDto(

	@NotBlank(message = ERROR_MESSAGE_DISPLAY_NAME_REQUIRED)
	@Length(max = 50, message = ERROR_MESSAGE_DISPLAY_NAME_LENGTH)
	String displayName,

	@NotNull(message = ERROR_MESSAGE_ACCOUNT_TYPE_REQUIRED)
	AccountType accountType
) {
	private static final String ERROR_MESSAGE_DISPLAY_NAME_REQUIRED = "You must enter a name";
	private static final String ERROR_MESSAGE_DISPLAY_NAME_LENGTH = "Your name must be between 1 and 50 characters";
	private static final String ERROR_MESSAGE_ACCOUNT_TYPE_REQUIRED = "You must choose an account type";
}
