package com.example.helloworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class HelloWorldController {

	@GetMapping(path = "/hello")
	public String helloWorld() {

		return """
				<html>
				  <body style="text-align: center; padding-top: 40px;">
				    <h1>Hello World. I love bacon!</h1>
				
				    <img
				      src="/images/grogu-bacon.png"
				      width="400px"
				    />
				
				    <p>Bacon photo by <a href="https://unsplash.com/@trendagraphy?utm_source=unsplash&utm_medium=referral&utm_content=creditCopyText">
				    James Trenda</a> on <a href="https://unsplash.com/photos/a-white-plate-topped-with-bacon-strips-on-top-of-a-table-IJIyIWZk-qg?utm_source=unsplash&utm_medium=referral&utm_content=creditCopyText">
				    Unsplash</a></p>
				  </body>
				</html>
				""";
	}
}