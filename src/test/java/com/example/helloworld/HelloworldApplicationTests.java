package com.example.helloworld;

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
		String greeting = controller.helloWorld();

		assertThat(greeting).isEqualTo("Hello World. I love bacon!");
	}
}
