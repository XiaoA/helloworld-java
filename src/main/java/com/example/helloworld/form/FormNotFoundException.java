package com.example.helloworld.form;

import java.util.UUID;

public class FormNotFoundException extends RuntimeException {

	private final UUID id;

	public FormNotFoundException(UUID id) {
		super(String.format("Form with ID '%s' does not exist", id));
		this.id = id;
	}

	public UUID getId() {
		return id;
	}
}
