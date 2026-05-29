package com.example.helloworld.account;

import java.util.UUID;

public record AccountDto (
	UUID id,
	String displayName,
	AccountType accountType
) {
}