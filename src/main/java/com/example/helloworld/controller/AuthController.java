package com.example.helloworld.controller;

import com.example.helloworld.model.dto.CurrentUserResponse;
import com.example.helloworld.model.dto.LoginRequest;
import com.example.helloworld.model.dto.LoginResponse;
import com.example.helloworld.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;

	public AuthController(AuthenticationManager authenticationManager, JwtService jwtService) {
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
	}

	@PostMapping("/login")
	public LoginResponse login(@Valid @RequestBody LoginRequest request) {
		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(
				request.username(),
				request.password()
			)
		);

		return new LoginResponse(jwtService.generateToken(authentication));
	}

	@GetMapping("/me")
	  public CurrentUserResponse me(Authentication authentication) {
		  return new CurrentUserResponse(
				  authentication.getName(),
				  authentication.getAuthorities()
					  .stream()
					  .map(GrantedAuthority::getAuthority)
					  .filter(authority -> authority.startsWith("ROLE_"))
					  .filter(authority -> !authority.startsWith("ROLE_FACTOR_"))
					  .map(authority -> authority.substring("ROLE_".length()))
					  .toList()
		  );
	  }
}
