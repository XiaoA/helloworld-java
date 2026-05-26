package com.example.helloworld.account;

public interface AccountMapper {

	CreateAccountRequest fromDto(CreateAccountRequestDto dto);
	UpdateAccountRequest fromDto(UpdateAccountRequestDto dto);
	AccountDto toDto(Account account);
}
