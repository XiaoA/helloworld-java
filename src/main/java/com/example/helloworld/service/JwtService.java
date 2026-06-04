package com.example.helloworld.service;

import java.time.Instant;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
	private final JwtEncoder jwtEncoder;

	public JwtService(JwtEncoder jwtEncoder) {
		this.jwtEncoder = jwtEncoder;
	}

	public String generateToken(Authentication authentication) {
		Instant now = Instant.now();
		List<String> roles = authentication.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority)
			.map(authority -> authority.startsWith("ROLE_") ? authority.substring("ROLE_".length()) : authority)
			.toList();

		JwtClaimsSet claims = JwtClaimsSet.builder()
			.issuer("helloworld")
			.subject(authentication.getName())
			.issuedAt(now)
			.expiresAt(now.plusSeconds(3600))
			.claim("roles", roles)
			.build();

		return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
	}
}
