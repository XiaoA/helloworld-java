package com.example.helloworld.service;

import com.example.helloworld.exception.UserNotFoundException;
import com.example.helloworld.model.dto.CreateUserRequestDto;
import com.example.helloworld.model.dto.UpdateUserRequestDto;
import com.example.helloworld.model.entity.Account;
import com.example.helloworld.exception.AccountNotFoundException;
import com.example.helloworld.repository.AccountRepository;
import com.example.helloworld.model.entity.User;
import com.example.helloworld.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
	private final UserRepository userRepository;
	private final AccountRepository accountRepository;

	public UserService(UserRepository userRepository, AccountRepository accountRepository) {
		this.userRepository = userRepository;
		this.accountRepository = accountRepository;
	}

	// Create User
	@Transactional
	public User createUser(UUID accountId, CreateUserRequestDto request) {
		Account account = getAccount(accountId);
		User user = new User();
		user.setDisplayName(request.displayName());
		user.setEmail(request.email());
		user.setRole(request.role());
		user.setAccount(account);
		account.addUser(user);

		return userRepository.save(user);
	}

	// List Users
	public List<User> listUsers(UUID accountId) {
		getAccount(accountId);
		return userRepository.findAllByAccountId(accountId);
	}

	// Get User
	public User getUser(UUID accountId, UUID userId) {
		return userRepository.findByIdAndAccountId(userId, accountId)
			.orElseThrow(() -> new UserNotFoundException(userId));
	}

	// Update User
	public User updateUser(UUID accountId, UUID userId, UpdateUserRequestDto request) {
		User user = getUser(accountId, userId);

		user.setDisplayName(request.displayName());
		user.setEmail(request.email());
		user.setRole(request.role());

		return userRepository.save(user);
	}

	// Delete User
	public void deleteUser(UUID accountId, UUID userId) {
		User user = getUser(accountId, userId);
		userRepository.delete(user);
	}

	// Find All
	public List<User> findAll() {
		return userRepository.findAll();
	}

	private Account getAccount(UUID accountId) {
		return accountRepository.findById(accountId)
			.orElseThrow(() -> new AccountNotFoundException(accountId));
	}
}
