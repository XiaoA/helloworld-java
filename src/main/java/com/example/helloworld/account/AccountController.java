package com.example.helloworld.account;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/accounts")
public class AccountController {
	private final AccountService accountService;

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}

	@GetMapping
	List<AccountDto> getAllAccounts() {
		return accountService.findAll()
			.stream()
			.map(this::toAccountDto)
			.toList();
	}

	@GetMapping("/{accountId}")
	AccountDto getAccountById(@PathVariable UUID accountId) {
		Account account = accountService.findById(accountId)
			.orElseThrow(() -> new AccountNotFoundException(accountId));

		return toAccountDto(account);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	AccountDto createAccount(@Valid @RequestBody CreateAccountRequest request) {
		return toAccountDto(accountService.createAccount(request));
	}

	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/{accountId}")
	AccountDto updateAccount(@PathVariable UUID accountId, @Valid @RequestBody UpdateAccountRequest request) {
		return toAccountDto(accountService.updateAccount(accountId, request));
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{accountId}")
	void deleteAccount(@PathVariable UUID accountId) {
		accountService.deleteAccount(accountId);
	}

	private AccountDto toAccountDto(Account account) {
		return new AccountDto(
			account.getId(),
			account.getDisplayName(),
			account.getAccountType()
		);
	}
}
