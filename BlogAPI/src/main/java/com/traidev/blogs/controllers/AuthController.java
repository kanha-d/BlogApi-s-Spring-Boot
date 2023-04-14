package com.traidev.blogs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.traidev.blogs.exceptions.ApiException;
import com.traidev.blogs.payloads.ApiResponse;
import com.traidev.blogs.payloads.JwtAuthRequest;
import com.traidev.blogs.payloads.JwtAuthResponse;
import com.traidev.blogs.payloads.UserDto;
import com.traidev.blogs.repos.UserRepo;
import com.traidev.blogs.security.JwtTokenHelper;
import com.traidev.blogs.services.UserService;

@RestController
public class AuthController {
	
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/token")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception{
		
		this.authenticate(request.getEmail(),request.getPassword());
		
		UserDetails user = this.userDetailsService.loadUserByUsername(request.getEmail());
		
		String generatedTokenString = jwtTokenHelper.generateToken(user);
		
		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(generatedTokenString);
		
		return new ResponseEntity<JwtAuthResponse>(response,HttpStatus.OK);
		
	}



	private void authenticate(String email, String password) throws Exception {
		
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email,password);

		
		try {
		
		this.authenticationManager.authenticate(authenticationToken);  
		}catch (BadCredentialsException e) {
			
			System.out.println("Invalid Details!");
			
			 throw new ApiException("Invalid username or password!");
		}
		
		
	}
	
	//Regeter new User
	@PostMapping("/register")
	private ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){
		
		UserDto registeredUserDto =  this.userService.registerUser(userDto);
		
		return new ResponseEntity<UserDto>(registeredUserDto,HttpStatus.CREATED);
		
	}

	
	
}
