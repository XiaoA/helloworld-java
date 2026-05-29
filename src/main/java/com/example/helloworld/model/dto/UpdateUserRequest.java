package com.example.helloworld.model.dto;

import com.example.helloworld.model.enums.AccountType;

public record UpdateUserRequest(
	String displayName,
	AccountType accountType
) {
}


