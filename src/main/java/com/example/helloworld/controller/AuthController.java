package com.example.helloworld.controller;

import com.example.helloworld.model.dto.LoginRequest;
import com.example.helloworld.model.dto.LoginResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	@PostMapping("/login")
	public LoginResponse login(@RequestBody LoginRequest request) {
		return new LoginResponse("Bearer token");
	}
}
