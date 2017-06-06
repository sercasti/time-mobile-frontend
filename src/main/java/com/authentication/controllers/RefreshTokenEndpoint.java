package com.authentication.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.authentication.config.WebSecurityConfig;
import com.authentication.extractor.TokenExtractor;
import com.authentication.model.User;
import com.authentication.model.token.JwtSettings;
import com.authentication.model.token.JwtToken;
import com.authentication.model.token.JwtTokenFactory;
import com.authentication.model.token.RawAccessJwtToken;
import com.authentication.model.token.RefreshToken;
import com.authentication.provider.JwtAuthenticationProvider;
import com.authentication.service.UserService;

@RestController
public class RefreshTokenEndpoint {
	@Autowired
	private JwtTokenFactory tokenFactory;
	@Autowired
	private JwtSettings jwtSettings;
	@Autowired
	private UserService userService;
	@Autowired
	@Qualifier("jwtHeaderTokenExtractor")
	private TokenExtractor tokenExtractor;

	@RequestMapping(value = "/refresh", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody JwtToken refreshToken(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String tokenPayload = tokenExtractor.extract(request.getHeader(WebSecurityConfig.JWT_TOKEN_HEADER_PARAM));

		RawAccessJwtToken rawToken = new RawAccessJwtToken(tokenPayload);
		RefreshToken refreshToken = RefreshToken.create(rawToken, jwtSettings.getTokenSigningKey())
				.orElseThrow(() -> new RuntimeException());
		String subject = refreshToken.getSubject();
		User user = userService.getByUsername(subject);
		return tokenFactory.createAccessJwtToken(user);
	}
	
	@RequestMapping(value = "/api/logout",method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody String logout(HttpServletRequest request){
		String tokenPayload = request.getHeader(WebSecurityConfig.JWT_TOKEN_HEADER_PARAM);
        RawAccessJwtToken token = new RawAccessJwtToken(tokenExtractor.extract(tokenPayload));
        JwtAuthenticationProvider.blacklistToken(token);
        return "{\"success\": \"token invalidated\"}";
	}
}
