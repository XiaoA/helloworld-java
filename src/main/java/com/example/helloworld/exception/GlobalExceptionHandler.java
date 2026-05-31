package com.example.helloworld.exception;

import com.example.helloworld.model.dto.ErrorDto;
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

	@ExceptionHandler(AccountNotFoundException.class)
	public ResponseEntity<ErrorDto> handleAccountNotFoundException(AccountNotFoundException ex) {
		ErrorDto errorDto = new ErrorDto(ex.getMessage());
		return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorDto> handleUserNotFoundException(UserNotFoundException ex) {
		ErrorDto errorDto = new ErrorDto(ex.getMessage());
		return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(FormNotFoundException.class)
	public ResponseEntity<ErrorDto> handleFormNotFoundException(FormNotFoundException ex) {
		ErrorDto errorDto = new ErrorDto(ex.getMessage());
		return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
	}
}
