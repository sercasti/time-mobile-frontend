package com.authentication.service;

import org.springframework.security.core.AuthenticationException;

import com.authentication.model.User;

public interface UserService {

	public User authenticate(String username, String password) throws AuthenticationException;

	public User getByUsername(String username) throws AuthenticationException;

}
