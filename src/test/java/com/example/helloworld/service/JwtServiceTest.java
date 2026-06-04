package com.example.helloworld.service;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

import static org.assertj.core.api.Assertions.assertThat;

class JwtServiceTest {

	@Test
	void generateTokenBuildsExpectedClaims() {
		CapturingJwtEncoder jwtEncoder = new CapturingJwtEncoder();
		JwtService jwtService = new JwtService(jwtEncoder);
		var authentication = new UsernamePasswordAuthenticationToken(
			"user",
			"password",
			List.of(
				new SimpleGrantedAuthority("ROLE_USER"),
				new SimpleGrantedAuthority("ROLE_ADMIN")
			)
		);

		String token = jwtService.generateToken(authentication);

		assertThat(token).isEqualTo("token-value");

		JwtClaimsSet claims = jwtEncoder.getParameters().getClaims();
		assertThat(claims.getClaimAsString("iss")).isEqualTo("helloworld");
		assertThat(claims.getSubject()).isEqualTo("user");
		assertThat(claims.getIssuedAt()).isNotNull();
		assertThat(claims.getExpiresAt()).isEqualTo(claims.getIssuedAt().plusSeconds(3600));
		assertThat(claims.getClaimAsStringList("roles")).containsExactly("USER", "ADMIN");
	}

	private static final class CapturingJwtEncoder implements JwtEncoder {
		private JwtEncoderParameters parameters;

		@Override
		public Jwt encode(JwtEncoderParameters parameters) {
			this.parameters = parameters;
			return new Jwt(
				"token-value",
				Instant.now(),
				Instant.now().plusSeconds(3600),
				Map.of("alg", "HS256"),
				Map.of("sub", "user")
			);
		}

		private JwtEncoderParameters getParameters() {
			return this.parameters;
		}
	}
}
