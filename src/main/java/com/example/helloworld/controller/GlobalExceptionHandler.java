package com.example.helloworld.controller;

import com.example.helloworld.domain.dto.ErrorDto;
import com.example.helloworld.account.AccountNotFoundException;
import com.example.helloworld.exception.TaskNotFoundException;
import com.example.helloworld.exception.UserAccountNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.UUID;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorDto> handleValidationExceptions(MethodArgumentNotValidException ex) {

		String errorMessage = ex.getBindingResult().getFieldErrors().stream()
			.findFirst()
			.map(DefaultMessageSourceResolvable::getDefaultMessage)
			.orElse( "Validation Failed.");

		ErrorDto errorDto = new ErrorDto(errorMessage);
		return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(TaskNotFoundException.class)
	public ResponseEntity<ErrorDto> handleTaskNotFoundException(TaskNotFoundException ex) {
		UUID taskNotFoundId = ex.getId();
		String errorMessage = String.format("Task with id %s not found", taskNotFoundId);
		ErrorDto errorDto = new ErrorDto(errorMessage);
		return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(UserAccountNotFoundException.class)
	public ResponseEntity<ErrorDto> handleUserAccountNotFoundException(UserAccountNotFoundException ex) {
		UUID userAccountNotFoundId = ex.getId();
		String errorMessage = String.format("User account with id %s not found", userAccountNotFoundId);
		ErrorDto errorDto = new ErrorDto(errorMessage);
		return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(AccountNotFoundException.class)
	public ResponseEntity<ErrorDto> handleAccountNotFoundException(AccountNotFoundException ex) {
		ErrorDto errorDto = new ErrorDto(ex.getMessage());
		return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
	}
}

