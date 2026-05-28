package com.example.helloworld.form;

import java.util.UUID;

public record FormDto(
	UUID id,
	String formType,
	String formTitle
) {
}
