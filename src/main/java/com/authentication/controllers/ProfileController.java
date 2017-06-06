package com.authentication.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.authentication.model.token.JwtAuthenticationToken;

@RestController
public class ProfileController {
	@RequestMapping(value = "/api/me",method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody String get(JwtAuthenticationToken token) {
		return "{\"username\":\"" + token.getPrincipal().toString() + "\"}";
	}
}