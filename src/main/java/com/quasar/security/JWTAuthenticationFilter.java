package com.quasar.security;

import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quasar.entity.User;
import com.quasar.util.Constants;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author emmanuel
 *
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;

	/**
	 * Constructor that sets the authentication manager.
	 * 
	 * @param authenticationManager		Object that processes an Authentication request.
	 */
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			
			String requestData = request.getReader().lines().collect(Collectors.joining());
			
			if(requestData.contains("&")) {
				requestData = "{\"" + requestData.replace("&", "\",\"").replace("=", "\":\"") + "\"}";
			}
			User user = new ObjectMapper().readValue(requestData, User.class);
			
			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					user.getUsername(), user.getPassword(), user.getAuthorities()));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication auth) throws IOException, ServletException {

		User user = (User) auth.getPrincipal();
		
		String token = Jwts.builder().setIssuedAt(new Date())
				.setSubject((((User)auth.getPrincipal()).getUsername()))
				.claim(Constants.ROLES, user.getAuthorities().stream()
					.map(GrantedAuthority::getAuthority)
					.collect(Collectors.toList()))
				.setExpiration(new Date(System.currentTimeMillis() + Constants.TOKEN_EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, Constants.SUPER_SECRET_KEY).compact();
		response.addHeader(Constants.HEADER_AUTHORIZACION_KEY, Constants.TOKEN_BEARER_PREFIX + " " + token);
	}
}