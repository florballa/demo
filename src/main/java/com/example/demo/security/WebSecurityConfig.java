package com.example.demo.security;

import com.example.demo.filter.CustomAuthenticationFilter;
import com.example.demo.filter.CustomAuthorizationFilter;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private CustomAuthorizationFilter customAuthorizationFilter;

	@Override
	protected void configure(HttpSecurity https) throws Exception {

		https.cors().and()
				.csrf().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and().authorizeRequests()
				.antMatchers("/general/**").permitAll()
				.antMatchers("/orders/basicOrders", "/items/**", "/orders").hasAuthority("MANAGER")
				.antMatchers("/users/**").hasAuthority("SYSADMIN")
				.anyRequest().authenticated()
				.and().authorizeRequests()
				.and()
				.addFilter(new CustomAuthenticationFilter(authenticationManagerBean(), userService))
				.addFilterBefore(customAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(daoAuthenticationProvider());
	}

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {

		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder.bCryptPasswordEncoder());
		provider.setUserDetailsService(userService);

		return provider;
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Bean
	public CorsFilter corsFilter() {
	  UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	  CorsConfiguration config = new CorsConfiguration();
	  config.setAllowCredentials(true);
	  config.addAllowedOriginPattern("*");
	  config.setExposedHeaders(Arrays.asList("access_token"));
	  config.addAllowedHeader("*");
	  config.addAllowedMethod("*");
	  source.registerCorsConfiguration("/**", config);
	  return new CorsFilter(source);
	}
}
