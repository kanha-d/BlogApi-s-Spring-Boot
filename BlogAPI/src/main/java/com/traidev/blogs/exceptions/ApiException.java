package com.traidev.blogs.exceptions;



public class ApiException extends RuntimeException{

	public ApiException(String message) {
		super(message);
		
	}
	
	
	public ApiException() {
		super();
	}

}
