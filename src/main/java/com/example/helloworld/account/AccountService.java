package com.example.helloworld.account;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AccountService {
	private final AccountRepository accountRepository;

	public AccountService(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	// Create Account
	public Account createAccount(CreateAccountRequest request) {
		Account account = new Account();
		account.setDisplayName(request.displayName());
		account.setAccountType(request.accountType());

		return accountRepository.save(account);
	}

	// List Accounts
	public List<Account> listAccounts() {
		return accountRepository.findAll();
	}

	// Get Account
	public Account getAccount(UUID accountId) {
		return accountRepository.findById(accountId)
			.orElseThrow(() -> new AccountNotFoundException(accountId));
	}

	// Update Account
	public Account updateAccount(UUID accountId, UpdateAccountRequest request) {
		Account account = getAccount(accountId);

		account.setDisplayName(request.displayName());
		account.setAccountType(request.accountType());

		return accountRepository.save(account);
	}

	// Delete Account
	public void deleteAccount(UUID accountId) {
		Account account = getAccount(accountId);
		accountRepository.delete(account);
	}
}