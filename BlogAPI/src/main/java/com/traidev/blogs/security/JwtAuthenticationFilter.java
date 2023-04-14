package com.traidev.blogs.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userDetails;
	
	@Autowired
	private JwtTokenHelper jwtHelper; 
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		
		//GetTokenFromRequest
		String requestToken = request.getHeader("Authorization");
		
		//BearerToken
		System.out.println("TOKEN"+requestToken);
		
		
		String username = null;
		
		String token = null;
		
		if(requestToken!=null && requestToken.startsWith("Bearer")) {
			
			token = requestToken.substring(7);
			
			
			try {
		    username = this.jwtHelper.getUsenameFromToken(token);
			
			}catch(IllegalArgumentException e) {
				System.out.println("Unable to get JWT Token");
			}catch(ExpiredJwtException e) {
				System.out.println("JWT Token has Expired");
			}catch(MalformedJwtException e) {
				System.out.println("Inavlid Token");
			}
			
			
		}else {
			System.out.println("Jwt Token not starts from Beared Token ");
		}
		
		
		//Once we get Token - need to validate now
		
		if(username!=null && SecurityContextHolder.getContext().getAuthentication() == null) {
			
			
			
			UserDetails userdetails = this.userDetails.loadUserByUsername(username);
			
			if(this.jwtHelper.validateToken(token, userdetails)){
				
				//Now Validated
				
				UsernamePasswordAuthenticationToken userPassAuth = new UsernamePasswordAuthenticationToken(userdetails,null,userdetails.getAuthorities());
				
				
				userPassAuth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				userPassAuth.getDetails();
				
				
				SecurityContextHolder.getContext().setAuthentication(userPassAuth);;
				 
				
				
			}else {
				System.out.println("Invalid Jwt Token! Details not Matched.");
			}
			
		}else {
			System.out.println("Username is Null!");
		}
		
		
		filterChain.doFilter(request, response);
		
		
		
		
	}

}
