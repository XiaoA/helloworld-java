package com.example.helloworld.controller;

import com.example.helloworld.model.dto.CreateUserRequestDto;
import com.example.helloworld.model.dto.UpdateUserRequestDto;
import com.example.helloworld.model.dto.UserDto;
import com.example.helloworld.model.entity.User;
import com.example.helloworld.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/accounts/{accountId}/users")
public class UserController {
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public List<UserDto> getAllUsers(@PathVariable UUID accountId) {
		return userService.listUsers(accountId)
			.stream()
			.map(this::toUserDto)
			.toList();
	}

	@GetMapping("/{userId}")
	public UserDto getUserById(@PathVariable UUID accountId, @PathVariable UUID userId) {
		return toUserDto(userService.getUser(accountId, userId));
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public UserDto createUser(@PathVariable UUID accountId, @Valid @RequestBody CreateUserRequestDto request) {
		return toUserDto(userService.createUser(accountId, request));
	}

	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/{userId}")
	public UserDto updateUser(@PathVariable UUID accountId, @PathVariable UUID userId, @Valid @RequestBody UpdateUserRequestDto request) {
		return toUserDto(userService.updateUser(accountId, userId, request));
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{userId}")
	public void deleteUser(@PathVariable UUID accountId, @PathVariable UUID userId) {
		userService.deleteUser(accountId, userId);
	}

	private UserDto toUserDto(User user) {
		return new UserDto(
			user.getId(),
			user.getDisplayName(),
			user.getEmail(),
			user.getRole()
		);
	}
}
