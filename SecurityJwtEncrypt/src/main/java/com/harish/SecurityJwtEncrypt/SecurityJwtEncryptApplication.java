package com.harish.SecurityJwtEncrypt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping("/myweb")
public class SecurityJwtEncryptApplication
{
	@Autowired
	private JwtUtilClass jwt;
	@Autowired 
	private CustomAuthFilter authfilter;
	@Autowired
	private AuthenticationManager am;
	@Autowired
	private CredentialsDao cd;
	
	BCryptPasswordEncoder encd=new BCryptPasswordEncoder(15);
	
	
	@PostMapping("/register")
	public String register(@RequestBody RegisterDetails rd)
	{
		rd.setPassword(encd.encode(rd.getPassword()));
		cd.save(rd);
		return "User got Registered";
	}
	
	@PostMapping("/login")
	public String login(@RequestBody loginRequests lg)
	{
		String name=lg.getName();
		String role=lg.getRole();
		String pass=lg.getPassword();
		
		System.out.println("entered login block !");
		am.authenticate(new UsernamePasswordAuthenticationToken(name,pass));
		
		String token=jwt.generateToken(name,role);
		
		return token;
	}
	@GetMapping("/aboutus")
	public String aboutus()
	{
		return "about us retured response after JWT Filter check !";
	}
	public static void main(String[] args)
	{
		SpringApplication.run(SecurityJwtEncryptApplication.class, args);
	}
}
