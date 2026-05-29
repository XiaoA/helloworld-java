package com.example.helloworld.model.dto;

import com.example.helloworld.model.enums.AccountType;

public record UpdateAccountRequest(
		String displayName,
		AccountType accountType
) {
}
