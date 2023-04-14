package com.traidev.blogs.payloads;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	
	private int id;
	
	@NotEmpty
	@Size(min=4,message="Name of User Required!")
	private String name;

	@Email(message="Email Address is not Valid !")
	private String email;
	
	@NotEmpty
	@Size(min=6,max=10,message="Password must be greater than 5 & Less than 10!")
	private String password;
	private String about;
	
	private Set<RoleDto> roles = new HashSet<>();

}
