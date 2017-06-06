package com.authentication.provider;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.authentication.model.token.JwtAuthenticationToken;
import com.authentication.model.token.JwtSettings;
import com.authentication.model.token.RawAccessJwtToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {
	private final JwtSettings jwtSettings;
	private final static Set<String> blacklist = ConcurrentHashMap.newKeySet();

	@Autowired
	public JwtAuthenticationProvider(JwtSettings jwtSettings) {
		this.jwtSettings = jwtSettings;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		RawAccessJwtToken rawAccessToken = (RawAccessJwtToken) authentication.getCredentials();
		
		if (blacklist.contains(rawAccessToken.getToken())) {
			throw new AuthenticationServiceException("Authentication Failed. Token logged out");
		}
		
		Jws<Claims> jwsClaims = rawAccessToken.parseClaims(jwtSettings.getTokenSigningKey());
		String subject = jwsClaims.getBody().getSubject();
		@SuppressWarnings("unchecked")
		List<String> scopes = jwsClaims.getBody().get("scopes", List.class);
		List<GrantedAuthority> authorities = scopes.stream().map(authority -> new SimpleGrantedAuthority(authority))
				.collect(Collectors.toList());
		return new JwtAuthenticationToken(subject, authorities);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
	}
	
	public static void blacklistToken(RawAccessJwtToken token){
		blacklist.add(token.getToken());
	}
}