package com.authentication.config;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.authentication.Application;
import com.authentication.filter.AjaxLoginProcessingFilter;
import com.authentication.filter.JwtTokenAuthenticationProcessingFilter;
import com.authentication.model.token.JwtTokenFactory;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class AuthenticationTest {

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;

	@Autowired
	private WebSecurityConfig webSecurityConfig;

	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Autowired
	private JwtTokenFactory jwtTokenFactory;

	@Before
	public void setup() throws Exception {
		AjaxLoginProcessingFilter filter = webSecurityConfig.buildAjaxLoginProcessingFilter();
		// mock authentication to decouple from userService
		filter.setAuthenticationManager(new MockAuthenticationManager());
		
		JwtTokenAuthenticationProcessingFilter jwtFilter = webSecurityConfig.buildJwtTokenAuthenticationProcessingFilter();
		this.mockMvc = webAppContextSetup(webApplicationContext).addFilter(jwtFilter, "/api/me").addFilter(filter, "/login").build();
	}

	@Test
	public void unsecuredFound() throws Exception {
		mockMvc.perform(post("/unsecured").contentType(contentType)).andExpect(status().isOk());
	}

	@Test
	public void login() throws Exception {
		String loginRequest = "{\"username\": \"test1\",\"password\": \"test1\"}";
		this.mockMvc
				.perform(post("/login").header("X-Requested-With", "XMLHttpRequest").contentType(contentType)
						.content(loginRequest))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString().contains("token");
	}

}

class MockAuthenticationManager implements AuthenticationManager {

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("USER"));
		return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), null, authorities);
	}

}