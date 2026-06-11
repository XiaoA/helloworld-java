package com.example.helloworld.model.dto;

import java.util.List;

public record CurrentUserResponse(
	String username,
	List<String> authorities
) {
}
