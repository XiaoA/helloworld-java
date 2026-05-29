package com.example.helloworld.controller;

import com.example.helloworld.model.dto.HelloWorldResponse;
import org.springframework.web.bind.annotation.*;


@RestController
public class HelloWorldController {

	@GetMapping("/hello")
	public HelloWorldResponse helloWorld() {
		return new HelloWorldResponse("Hello from Spring Boot!");
	}
}
