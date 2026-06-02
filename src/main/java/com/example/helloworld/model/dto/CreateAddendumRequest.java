package com.example.helloworld.model.dto;

import com.example.helloworld.model.enums.AddendumType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateAddendumRequest(

	@NotBlank(message = ERROR_MESSAGE_ADDENDUM_TITLE_REQUIRED)
	String addendumTitle,

	@NotBlank(message = ERROR_MESSAGE_ADDENDUM_TEXT_REQURED)
		String addendumText,

	@NotNull(message = ERROR_MESSAGE_ADDENDUM_TYPE_REQURED)
	AddendumType addendumType,

	@NotBlank(message = ERROR_MESSAGE_R_NUMBER_REQURED)
  String rNumber,

	@NotBlank(message = ERROR_MESSAGE_DEPT_INFORMATION_REQURED)
	List<String> deptInformation
) {
	private static final String ERROR_MESSAGE_ADDENDUM_TITLE_REQUIRED = "Addendum title is required";
	private static final String ERROR_MESSAGE_ADDENDUM_TEXT_REQURED = "Addendum text is required";
	private static final String ERROR_MESSAGE_ADDENDUM_TYPE_REQURED = "Addendum type is required";
	private static final String ERROR_MESSAGE_R_NUMBER_REQURED = "R number is required";
	private static final String ERROR_MESSAGE_DEPT_INFORMATION_REQURED = "Department information is required";
}
