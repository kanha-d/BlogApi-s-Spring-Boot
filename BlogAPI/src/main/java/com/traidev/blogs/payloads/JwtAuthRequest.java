package com.traidev.blogs.payloads;

import lombok.Data;

@Data
public class JwtAuthRequest {

	private String email;
	private String password; 
}
