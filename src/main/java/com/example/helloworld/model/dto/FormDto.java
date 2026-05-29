package com.example.helloworld.model.dto;

import com.example.helloworld.model.enums.FormType;

import java.util.UUID;

public record FormDto(
	UUID id,
	FormType formType,
	String formTitle
) {
}
