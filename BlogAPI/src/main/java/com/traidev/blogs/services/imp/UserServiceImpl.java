package com.traidev.blogs.services.imp;

import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.traidev.blogs.entities.Role;
import com.traidev.blogs.entities.User;
import com.traidev.blogs.exceptions.ResourceNotFoundException;
import com.traidev.blogs.payloads.UserDto;
import com.traidev.blogs.repos.*;
import com.traidev.blogs.services.UserService;


@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private PasswordEncoder passwordEnc;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		
		User user = this.dtoToUser(userDto);
		
		this.userRepo.save(user);
		
		return this.userToDto(user);
	}

	@Override
	public UserDto updateUser(UserDto userdto, Integer userId) {
		
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User"," Id ",userId));
		
		
		user.setName(userdto.getName());
		user.setEmail(userdto.getEmail());
		user.setPassword(userdto.getPassword());
		user.setAbout(userdto.getAbout());
		
		User updatedUser = this.userRepo.save(user);
		
		UserDto updateUserDto = this.userToDto(updatedUser);
		
		
		
		return updateUserDto;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User"," Id ",userId));

		UserDto userDto = this.userToDto(user);
		
		return userDto;
	}

	@Override
	public List<UserDto> getAllUsers() {
		
		
		List<User> users = this.userRepo.findAll();
		
		List<UserDto> userDtos = users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
		
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		
		
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
		this.userRepo.delete(user);

		
	}
	
	private User dtoToUser(UserDto userDto) {
		
		
		User user = this.modelMapper.map(userDto, User.class);
		
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setAbout(userDto.getAbout());
//		user.setPassword(userDto.getPassword());
		
		
		return user;
	}
	
	
	public UserDto userToDto(User user) {
		
		UserDto userDto =  this.modelMapper.map(user, UserDto.class);
		
		return userDto;
		
	}

	@Override
	public UserDto registerUser(UserDto userDto) {
		
	User user = this.modelMapper.map(userDto, User.class);
	
	//We Encoded the Password
	user.setPassword(this.passwordEnc.encode(userDto.getPassword()));
	
	//Roles Assign 
	
	Role role = this.roleRepo.findById(502).get();
	user.getRoles().add(role);
	
	User newUser = this.userRepo.save(user);
	
	return this.modelMapper.map(newUser, UserDto.class);
	
	
		
	}

}
