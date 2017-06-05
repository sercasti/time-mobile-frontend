package com.authentication.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.authentication.model.User;
import com.authentication.service.UserService;

@Service
public class MockUserServiceImpl implements UserService {

	private final static Map<String, String> usersAndPassMap = new HashMap<String, String>();

	static {
		usersAndPassMap.put("test1", "test1");
		usersAndPassMap.put("test2", "test2");
	}

	@Override
	public User authenticate(String username, String password) throws AuthenticationException {
		String thePassword = usersAndPassMap.get(username);
		if (StringUtils.isBlank(thePassword))
			throw new UsernameNotFoundException("User not found: " + username);

		if (!StringUtils.equalsIgnoreCase(thePassword, password)) {
			throw new BadCredentialsException("Authentication Failed. Username or Password not valid.");
		}
		return new User(username);
	}

	@Override
	public User getByUsername(String username) {
		String thePassword = usersAndPassMap.get(username);
		if (StringUtils.isBlank(thePassword))
			throw new UsernameNotFoundException("User not found: " + username);
		return new User(username);
	}
}