package com.example.helloworld.model.dto;

import com.example.helloworld.model.enums.FormType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateFormRequestDto(

	@NotNull(message = ERROR_MESSAGE_FORM_TYPE_REQUIRED)
	FormType formType,

	@NotBlank(message = ERROR_MESSAGE_FORM_TITLE_REQUIRED)
	String formTitle
) {
	private static final String ERROR_MESSAGE_FORM_TYPE_REQUIRED = "Form type is required";
	private static final String ERROR_MESSAGE_FORM_TITLE_REQUIRED = "Form title is required";
}
