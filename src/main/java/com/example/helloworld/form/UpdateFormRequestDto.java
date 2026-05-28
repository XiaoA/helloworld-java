package com.example.helloworld.form;

import jakarta.validation.constraints.NotBlank;

public record UpdateFormRequestDto(

	@NotBlank(message = ERROR_MESSAGE_FORM_TYPE_REQUIRED)
	FormType formType,

	@NotBlank(message = ERROR_MESSAGE_FORM_TITLE_REQUIRED)
	String formTitle
) {
	private static final String ERROR_MESSAGE_FORM_TYPE_REQUIRED = "Form type is required";
	private static final String ERROR_MESSAGE_FORM_TITLE_REQUIRED = "Form title is required";
}
