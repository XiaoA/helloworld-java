package com.example.helloworld.account;
import java.util.UUID;

public class AccountNotFoundException extends RuntimeException {
	public AccountNotFoundException(UUID accountId) {
		super(STR."Account not found: \{accountId}");
	}
}