package com.example.helloworld.controller;

import com.example.helloworld.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.mockito.Mockito.when;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class SecuritySmokeTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AccountService accountService;

    @Test
    void helloShouldBePublic() throws Exception {
        mockMvc.perform(get("/hello"))
            .andExpect(status().isOk());
    }

    @Test
    void accountsShouldRequireAuthentication() throws Exception {
        mockMvc.perform(get("/api/v1/accounts"))
            .andExpect(status().isUnauthorized());
    }

	@Test
	void accountsShouldAllowAuthenticatedUser() throws Exception {
		when(accountService.findAll()).thenReturn(List.of());

		mockMvc.perform(get("/api/v1/accounts")
				.with(user("user").roles("USER")))
			.andExpect(status().isOk());
	}
}
