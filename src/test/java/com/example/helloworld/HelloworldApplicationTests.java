package com.example.helloworld;

import com.example.helloworld.controller.HelloWorldController;
import com.example.helloworld.domain.dto.HelloWorldResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class HelloworldApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testDisplayHelloWorld() {
		HelloWorldController controller = new HelloWorldController();
		HelloWorldResponse greeting = controller.helloWorld();

		assertThat(greeting).isNotNull();
		assertThat(greeting.message()).isEqualTo("Hello from Spring Boot!");
	}
}
