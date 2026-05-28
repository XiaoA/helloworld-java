package com.example.helloworld.user;

import com.example.helloworld.account.AccountType;

public record UpdateUserRequest(
	String displayName,
	AccountType accountType
) {
}


