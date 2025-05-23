package com.harish.SecurityJwtEncrypt;

import java.security.Security;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthFilter implements AuthenticationProvider
{
	
	@Autowired
	private CredentialsDao dao;
	
	BCryptPasswordEncoder encd=new BCryptPasswordEncoder(15);

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException
	{
		System.out.println("entered custom auth");
		String username=authentication.getName();
		String password=authentication.getCredentials().toString();
		
		RegisterDetails rd= dao.findByName(username).orElseThrow();
		System.out.println(password);
		System.out.println(rd.getPassword());
		if(encd.matches(password,rd.getPassword()))
		{
			Authentication auth=new UsernamePasswordAuthenticationToken(
					username,
					password
					);
			SecurityContextHolder.getContext().setAuthentication(auth);
			return auth;
		}
		else
		{
			throw new BadCredentialsException("Invalid Password !");
		}
		
	}

	@Override
	public boolean supports(Class<?> authentication)
	{
		// TODO Auto-generated method stub
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
