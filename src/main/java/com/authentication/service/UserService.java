package com.authentication.service;

import org.springframework.security.core.AuthenticationException;

public interface UserService {

	public String authenticate(String username, String password) throws AuthenticationException;

	public String getByUsername(String username) throws AuthenticationException;

}
