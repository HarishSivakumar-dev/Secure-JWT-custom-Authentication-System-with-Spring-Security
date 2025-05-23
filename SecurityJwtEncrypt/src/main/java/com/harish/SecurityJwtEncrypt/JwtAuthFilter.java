package com.harish.SecurityJwtEncrypt;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component 
public class JwtAuthFilter extends OncePerRequestFilter
{
	@Autowired
	private JwtUtilClass util;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException
	{
		
		String path=request.getRequestURI();
		System.out.println("entered jwt auth");
		if(path.equals("/myweb/login") || path.equals("/myweb/register"))
		{
			System.out.println("redirected from jwt auth");
			filterChain.doFilter(request, response);
			return;
		}
		else
		{
			String auth=request.getHeader("Authorization");
			
			if(auth!=null && auth.startsWith("Bearer"))
			{
				String token=auth.substring(7);
				if(util.validateToken(token))
				{
					String user=util.extractUser(token);
					Authentication ath=new UsernamePasswordAuthenticationToken(
							user,
							null,
							List.of());
					SecurityContextHolder.getContext().setAuthentication(ath);
					filterChain.doFilter(request, response);
				}
				else
				{
					throw new BadCredentialsException("No Valid JWT");
				}
			}
			else
			{
				throw new BadCredentialsException("No Valid JWT");
			}
			
		}
	}

}
