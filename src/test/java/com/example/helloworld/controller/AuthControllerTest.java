package com.example.helloworld.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.matchesPattern;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Test
	void loginShouldReturnToken() throws Exception {
		mockMvc.perform(post("/api/v1/auth/login")
				.contentType("application/json")
			.content("""
					{
					  "username": "user",
					  "password": "user"
					}
					"""))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.token").value(matchesPattern("^[^.]+\\.[^.]+\\.[^.]+$")));
	}

	@Test
	void loginShouldRejectBadCredentials() throws Exception {
		mockMvc.perform(post("/api/v1/auth/login")
				.contentType("application/json")
				.content("""
					{
					  "username": "user",
					  "password": "wrong-password"
					}
					"""))
			.andExpect(status().isUnauthorized());
	}

	@Test
	void bearerTokenShouldAuthorizeProtectedEndpoint() throws Exception {
		String token = extractToken();

		mockMvc.perform(get("/api/v1/accounts")
				.header(AUTHORIZATION, "Bearer " + token))
			.andExpect(status().isOk());
	}

	private String extractToken() throws Exception {
		String response = mockMvc.perform(post("/api/v1/auth/login")
				.contentType("application/json")
				.content("""
					{
					  "username": "user",
					  "password": "user"
					}
					"""))
			.andExpect(status().isOk())
			.andReturn()
			.getResponse()
			.getContentAsString();

		Matcher matcher = Pattern.compile("\"token\"\\s*:\\s*\"([^\"]+)\"").matcher(response);
		if (!matcher.find()) {
			throw new IllegalStateException("Login response did not contain a token");
		}

		return matcher.group(1);
	}
}
