package com.harish.SecurityJwtEncrypt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
@EnableWebSecurity
public class SecurityConfig
{
	@Autowired
	private CustomAuthFilter caf;
	
	@Autowired
	private JwtAuthFilter jwt;
	
	@Bean
	public SecurityFilterChain securityfilterchain(HttpSecurity http) throws Exception
	{
		System.out.println("entered security config");
		return http.csrf(customizer->customizer.disable())
				   .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				   .authorizeHttpRequests(requests->requests.requestMatchers("/myweb/register","/myweb/login").permitAll().anyRequest().authenticated())
				   .authenticationProvider(caf)
				   .addFilterBefore(jwt,UsernamePasswordAuthenticationFilter.class)
				   .build();
	}
	
	@Bean
	public AuthenticationManager authmanager(HttpSecurity http) throws Exception
	{
		return http.getSharedObject(AuthenticationManagerBuilder.class)
				   .authenticationProvider(caf)
				   .build();
	}

}
