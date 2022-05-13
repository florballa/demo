package com.example.demo.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;
	private final UserService userService;

	public CustomAuthenticationFilter(AuthenticationManager authenticationManager, UserService userService) {
		this.authenticationManager = authenticationManager;
		this.userService = userService;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		String username = request.getParameter("username");
		String password = request.getParameter("password");

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);
		return authenticationManager.authenticate(authenticationToken);

	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authentication) throws IOException, ServletException {
		User user = (User) authentication.getPrincipal();
		Algorithm algorithm = Algorithm.HMAC256("s3Cr3TT0ken0fors3cur1tY".getBytes());
		String access_token = JWT.create().withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + 3 * 60 * 60 * 1000))
				.withIssuer(request.getRequestURL().toString())
				.withClaim("roles",
						user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.sign(algorithm);

		response.setHeader("access_token", access_token);
    	response.getWriter().write(new ObjectMapper().writeValueAsString(userService.findByUsername(user.getUsername()).get()));
	}
}
