package com.traidev.blogs.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.traidev.blogs.payloads.ApiResponse;
import com.traidev.blogs.payloads.UserDto;
import com.traidev.blogs.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController  {
	
	@Autowired
	private UserService userService;
	
	//Create User
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto)
	{
		UserDto createUserDto = this.userService.createUser(userDto);
		return new ResponseEntity<>(createUserDto,HttpStatus.CREATED);
 	}
	
	//Update User
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable("userId") Integer userId)
	{
		
		UserDto updatedUser = this.userService.updateUser(userDto, userId);
		
		return  ResponseEntity.ok(updatedUser);
		
	}
	
	//Delete User
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer uid)
	{
		this.userService.deleteUser(uid);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted Succefully",true),HttpStatus.OK);
		
	}
	
	//Get user by id
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUser(@PathVariable("userId") Integer userId)
	{
		
		UserDto userData = this.userService.getUserById(userId);
		
		return  ResponseEntity.ok(userData);
		
	}
	
	//Get all users
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers()
	{
		
		List<UserDto> usersList = this.userService.getAllUsers();
		
		return  ResponseEntity.ok(usersList);
		
	}
	
	
	
	

}
