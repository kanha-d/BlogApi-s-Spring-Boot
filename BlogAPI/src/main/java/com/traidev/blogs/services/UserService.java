package com.traidev.blogs.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.traidev.blogs.payloads.UserDto;


public interface UserService {
	
	UserDto createUser(UserDto user);
	
	UserDto registerUser(UserDto user);
	
	
	UserDto updateUser(UserDto user,Integer userId);
	UserDto getUserById(Integer userId);
	List<UserDto> getAllUsers();
	void deleteUser(Integer userId);
	 

}
