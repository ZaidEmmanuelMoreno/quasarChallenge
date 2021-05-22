package com.quasar.security;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.quasar.util.Constants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

/**
 * @author emmanuel
 *
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
	
	/**
	 * Constructor that sets the AuthenticationManager
	 * 
	 * @param authManager
	 */
	public JWTAuthorizationFilter(AuthenticationManager authManager) {
		super(authManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		String header = req.getHeader(Constants.HEADER_AUTHORIZACION_KEY);
		if (header == null || !header.startsWith(Constants.TOKEN_BEARER_PREFIX)) {
			chain.doFilter(req, res);
			return;
		}
		UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(req, res);
	}

	/**
	 * Method that builds the jwt token for the access to application resources.
	 * 
	 * @param request				Request information for HTTP servlets.
	 * @return						null if user does not exists.
	 */
	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(Constants.HEADER_AUTHORIZACION_KEY).replaceAll(Constants.TOKEN_BEARER_PREFIX, "");
		UsernamePasswordAuthenticationToken user = null;
		
		if (token != null) {
			Claims claims = Jwts.parser()
						.setSigningKey(Constants.SUPER_SECRET_KEY)
						.parseClaimsJws(token)
						.getBody();

			if (claims != null) {
				if(claims.get(Constants.ROLES) != null) {
					user = setUpUsernamePasswordAuthenticationToken(claims);
				} else {
					SecurityContextHolder.clearContext();
				}
				return user;
			}
			return null;
		}
		return null;
	}

	/**
	 * Method that builds the user with the credentials of the user logged.
	 * 
	 * @param claims				Information in the jwt.
	 * @return						user with username and authorities.
	 */
	private UsernamePasswordAuthenticationToken setUpUsernamePasswordAuthenticationToken(Claims claims) {
		@SuppressWarnings("unchecked")
		List<String> authorities = (List<String>) claims.get(Constants.ROLES);

		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null,
				authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
		SecurityContextHolder.getContext().setAuthentication(auth);
		return auth;
	}
}