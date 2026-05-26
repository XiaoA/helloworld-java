package com.example.helloworld.account;

import java.util.UUID;

public record CreateAccountRequest(
	String displayName,
	AccountType accountType
) {
}
