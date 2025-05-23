package com.harish.SecurityJwtEncrypt;

import java.time.Instant;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtilClass
{
	final String tokenkey ="kencpth@232*kefnn@123%fif&woj#efwpowf@169&ryedvn";
	final SecretKey key=Keys.hmacShaKeyFor(tokenkey.getBytes());
	
	
	@SuppressWarnings("deprecation")
	public String generateToken(String name, String role)
	{
		String username=name;
		String roleof=role;
		Instant is=Instant.now();
		
		return Jwts.builder()
				   .setSubject(username)
				   .setIssuedAt(Date.from(is))
				   .setExpiration(Date.from(is.plusSeconds(3600)))
				   .signWith(key)
				   .compact();
	}
	
	@SuppressWarnings("deprecation")
	public String extractUser(String token)
	{
		Claims claim=Jwts.parser().setSigningKey(key).build().parseClaimsJws(token).getBody();
		
		return claim.toString();
	}
	
	@SuppressWarnings("deprecation")
	public boolean validateToken(String token)
	{
		try
		{
			Jwts.parser().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		}
		catch(Exception e)
		{
			System.out.println(e);
			return false;
		}
	}

}
