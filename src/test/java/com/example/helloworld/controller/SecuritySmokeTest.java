package com.example.helloworld.controller;

import com.example.helloworld.model.entity.Account;
import com.example.helloworld.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@SpringBootTest
@AutoConfigureMockMvc
class SecuritySmokeTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void helloShouldBePublic() throws Exception {
        mockMvc.perform(get("/hello"))
            .andExpect(status().isOk());
    }

	@Test
	void loginShouldBePublic() throws Exception {
		mockMvc.perform(post("/api/v1/auth/login")
				.contentType("application/json")
				.content("""
                    {
                      "username": "user",
                      "password": "user"
                    }
                    """))
			.andExpect(status().isOk());
	}

    @Test
    void accountsShouldRequireAuthentication() throws Exception {
        mockMvc.perform(get("/api/v1/accounts"))
            .andExpect(status().isUnauthorized());
    }

	@Test
	void accountsShouldAllowAuthenticatedUser() throws Exception {
		mockMvc.perform(get("/api/v1/accounts")
				.with(user("user").roles("USER")))
			.andExpect(status().isOk());
	}

	@Test
	void adminOnlyEndpointsShouldRejectNonAdmins() throws Exception {
		mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete("/api/v1/accounts/" + UUID.randomUUID())
				.with(user("user").roles("USER")))
			.andExpect(status().isForbidden());
	}

	@TestConfiguration
	static class TestConfig {
		@Bean
		@Primary
		AccountService accountService() {
			return new AccountService(null) {
				@Override
				public List<Account> findAll() {
					return List.of();
				}

				@Override
				public Optional<Account> findById(UUID accountId) {
					return Optional.empty();
				}
			};
		}
	}
}
