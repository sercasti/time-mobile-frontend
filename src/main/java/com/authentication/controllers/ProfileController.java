package com.authentication.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.authentication.model.User;
import com.authentication.model.token.JwtAuthenticationToken;

@RestController
public class ProfileController {
	@RequestMapping(value = "/api/me", method = RequestMethod.GET)
	public @ResponseBody User get(JwtAuthenticationToken token) {
		return (User) token.getPrincipal();
	}
}