package com.example.helloworld.model.dto;

import com.example.helloworld.model.enums.UserRole;

import java.util.UUID;

public record UserDto(
	UUID id,
	String displayName,
	String email,
	UserRole role
) {
}
