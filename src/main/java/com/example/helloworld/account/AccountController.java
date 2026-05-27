package com.example.helloworld.account;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/accounts")
public class AccountController {

	private final AccountService accountService;

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}

	// Get All Accounts
	@GetMapping("")
	List<Account> findAll() {
		return accountService.findAll();
	}

	// Get Account By Id
	@GetMapping("/{accountId}")
	Account findById(@PathVariable UUID accountId) {
		Optional<Account> account = accountService.findById(accountId);
		if (account.isEmpty()) {
			throw new AccountNotFoundException(accountId);
		}
		return account.get();
	}

	// Create Account
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	AccountDto createAccount(@Valid @RequestBody CreateAccountRequest request) {
		Account account = accountService.createAccount(request);

		return new AccountDto(
			account.getId(),
			account.getDisplayName(),
			account.getAccountType()
		);
	}

	// Update Account
	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/{accountId}")
	Account updateAccount(@PathVariable UUID accountId, @Valid @RequestBody UpdateAccountRequest request) {
		return accountService.updateAccount(accountId, request);
	}

	// Delete Account
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{accountId}")
	void deleteAccount(@PathVariable UUID accountId) {
		accountService.deleteAccount(accountId);
	}

}