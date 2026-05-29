package com.example.helloworld.model.dto;

import com.example.helloworld.model.enums.FormType;

public record UpdateFormRequest(
	FormType formType,
	String formTitle
) {
}



