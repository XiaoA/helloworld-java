package com.example.helloworld.controller;

import com.example.helloworld.exception.AccountNotFoundException;
import com.example.helloworld.model.dto.AccountDto;
import com.example.helloworld.model.dto.CreateAccountRequest;
import com.example.helloworld.model.dto.UpdateAccountRequest;
import com.example.helloworld.model.entity.Account;
import com.example.helloworld.service.AccountService;
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
	public List<AccountDto> getAllAccounts() {
		return accountService.findAll()
			.stream()
			.map(this::toAccountDto)
			.toList();
	}

	@GetMapping("/{accountId}")
	public AccountDto getAccountById(@PathVariable UUID accountId) {
		Account account = accountService.findById(accountId)
			.orElseThrow(() -> new AccountNotFoundException(accountId));

		return toAccountDto(account);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public AccountDto createAccount(@Valid @RequestBody CreateAccountRequest request) {
		return toAccountDto(accountService.createAccount(request));
	}

	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/{accountId}")
	public AccountDto updateAccount(@PathVariable UUID accountId, @Valid @RequestBody UpdateAccountRequest request) {
		return toAccountDto(accountService.updateAccount(accountId, request));
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{accountId}")
	public void deleteAccount(@PathVariable UUID accountId) {
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
