package com.authentication.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {

	@RequestMapping("/unsecured")
	String unsecuredUrl() {
		return "This is a path you can access without logging on or having the jwt token";
	}
	
	@RequestMapping("/")
	String hello() {
		return "use a POST request to /login and get your jwt token, or just visit /unsecured";
	}
	
	@RequestMapping("/api/**")
	String protectedUrl() {
		return "you should only be able to get here if you have a valid jwt token";
	}

}
