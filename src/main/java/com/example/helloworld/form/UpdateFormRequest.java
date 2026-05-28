package com.example.helloworld.form;

public record UpdateFormRequest(
	FormType formType,
	String formTitle
) {
}



