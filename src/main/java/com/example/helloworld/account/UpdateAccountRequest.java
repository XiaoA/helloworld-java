package com.example.helloworld.account;

import java.util.UUID;

public record UpdateAccountRequest(
		String displayName,
		AccountType accountType
) {
}
