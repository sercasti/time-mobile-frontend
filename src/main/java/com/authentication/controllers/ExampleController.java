package com.authentication.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {

	@RequestMapping("/unsecured")
	String unsecuredUrl() {
		return "This is a path you can access without logging on or having the jwt token";
	}
	
	@RequestMapping("/")
	String hello(HttpServletRequest request) {
	    String uri = request.getRequestURL().toString();
		return "<ul> <li>To login, send a POST to " + uri +"login </li></br>"
				+ "<li>Secured endpoint example: " + uri +"api/me </li></br>"
				+ "<li>Unsecured endpoint example: " + uri +"unsecured </li></br>"
				+ "<li>To logout, send a GET request to: " + uri +"api/logout</li>"
				+ "</ul></br></br></br>"
		        + "<b>login headers:</b></br>"
		        + "X-Requested-With: XMLHttpRequest</br>"  
				+ "Content-Type: application/json</br>"
				+ "Cache-Control: no-cache</br>"
				+ "login body:</br>"
				+ "{</br>"
				+ "    \"username\": \"test1\",</br>"
				+ "    \"password\": \"test1\"</br>"
				+ "}"
				+ "</br>"
				+ "<b>login response:</b></br>"
				+ "{</br> "
				+ "\"token\": \"eyJ... the token to send on each request on the X-Authorization header\", </br>"
				+ "\"refreshToken\": \"eyJhbGciO... used to acquire new Access Token calling " + uri +"refresh \"</br>"
				+ "}</br></br></br>"
				+ "<b>Sample GET request:</b></br>"
				+ "GET /api/me HTTP/1.1</br>"
				+ "Host: localhost:8080</br>"
                + "X-Authorization: <b>Bearer</b> eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0MSIsInNjb3BlcyI6IlVTRVIiLCJpc3MiOiJodHRwOi8vdGVzdGluZy5jb20iLCJpYXQiOjE0OTY3MDY4MjQsImV4cCI6MTQ5NjcwNzcyNH0.0L68nr2NapNKdXmahAgD_1AzO_BMvCwQTUdg6x53awuPi5slpiXj-j0B7cjrprCc_ZSj7dZ-DxJcLsqpOodhwQ";
	}
	
	@RequestMapping("/api/**")
	String protectedUrl() {
		return "you should only be able to get here if you have a valid jwt token";
	}

}
