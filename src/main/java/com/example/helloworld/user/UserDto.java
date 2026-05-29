package com.example.helloworld.user;

import java.util.UUID;

public record UserDto(
	UUID id,
	String displayName,
	String email,
	UserRole role
) {
}
