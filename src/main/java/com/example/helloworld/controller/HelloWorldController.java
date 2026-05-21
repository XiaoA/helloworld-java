package com.example.helloworld.controller;

import com.example.helloworld.domain.dto.HelloWorldResponse;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class HelloWorldController {

	@GetMapping("/hello")
	public HelloWorldResponse helloWorld() {
		return new HelloWorldResponse("Hello from Spring Boot!");
	}
}
