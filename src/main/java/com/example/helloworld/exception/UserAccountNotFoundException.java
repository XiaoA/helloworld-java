package com.example.helloworld.exception;

import java.util.UUID;

public class UserAccountNotFoundException extends RuntimeException {

	private final UUID id;

	public UserAccountNotFoundException(UUID id) {
		super(String.format("User account with ID '%s' does not exist", id));
		this.id = id;
	}

	public UUID getId() {
		return id;
	}
}
