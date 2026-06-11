package com.example.helloworld.controller;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;

@SpringBootTest(properties = "app.jwt.secret=MDEyMzQ1Njc4OWFiY2RlZjAxMjM0NTY3ODlhYmNkZWY=")
@AutoConfigureMockMvc
class AuthControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Test
	void loginShouldReturnToken() throws Exception {
		mockMvc.perform(post("/api/v1/auth/login")
				.contentType(APPLICATION_JSON)
				.content(loginRequest("user", "user")))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.token").value(matchesPattern("^[^.]+\\.[^.]+\\.[^.]+$")));
	}

	@Test
	void loginShouldRejectBadCredentials() throws Exception {
		mockMvc.perform(post("/api/v1/auth/login")
				.contentType(APPLICATION_JSON)
				.content(loginRequest("user", "wrong-password")))
			.andExpect(status().isUnauthorized());
	}

	@Test
	void bearerTokenShouldAuthorizeProtectedEndpoint() throws Exception {
		String token = extractToken();

		mockMvc.perform(get("/api/v1/accounts")
				.header(AUTHORIZATION, "Bearer " + token))
			.andExpect(status().isOk());

		mockMvc.perform(get("/api/v1/accounts"))
			.andExpect(status().isUnauthorized());
	}

	private String extractToken() throws Exception {
		String response = mockMvc.perform(post("/api/v1/auth/login")
				.contentType(APPLICATION_JSON)
				.content(loginRequest("user", "user")))
			.andExpect(status().isOk())
			.andReturn()
			.getResponse()
			.getContentAsString();

		String token = JsonPath.read(response, "$.token");
		if (token == null || token.isBlank()) {
			throw new IllegalStateException("Login response did not contain a token");
		}

		return token;
	}

	@Test
	void meShouldReturnCurrentUserForBearerToken() throws Exception {
		String token = extractToken();

		mockMvc.perform(get("/api/v1/auth/me")
				.header(AUTHORIZATION, "Bearer " + token))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.username").value("user"))
			.andExpect(jsonPath("$.authorities").isArray())
			.andExpect(jsonPath("$.authorities").value(hasItem("USER")))
			.andExpect(jsonPath("$.authorities").value(not(hasItem("FACTOR_PASSWORD"))));
	}

	@Test
	void meShouldRejectUnauthenticatedRequest() throws Exception {
		mockMvc.perform(get("/api/v1/auth/me"))
			.andExpect(status().isUnauthorized());
	}

	private static String loginRequest(String username, String password) {
		return """
			{
			  "username": "%s",
			  "password": "%s"
			}
			""".formatted(username, password);
	}
}
