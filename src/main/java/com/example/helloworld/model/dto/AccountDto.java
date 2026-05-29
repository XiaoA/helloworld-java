package com.example.helloworld.model.dto;

import com.example.helloworld.model.enums.AccountType;

import java.util.UUID;

public record AccountDto (
	UUID id,
	String displayName,
	AccountType accountType
) {
}