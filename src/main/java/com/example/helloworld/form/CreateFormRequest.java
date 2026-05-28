package com.example.helloworld.form;

public record CreateFormRequest(
	FormType formType,
	String formTitle
) {
}
