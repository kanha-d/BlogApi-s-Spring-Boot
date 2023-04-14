package com.traidev.blogs;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.traidev.blogs.entities.Role;
import com.traidev.blogs.repos.RoleRepo;

import antlr.collections.List;

@SpringBootApplication
public class BlogApiApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
	
	public static void main(String[] args) {
		SpringApplication.run(BlogApiApplication.class, args);
	}
	
	//132.154.57.170
	
	@Bean
	public ModelMapper modelMapper() {
		
		return new ModelMapper();
		
	}

	@Override
	public void run(String... args) throws Exception {
	 
	 try {
		 Role role = new Role();
		 role.setId(501);
		 role.setName("ROLE_ADMIN");
		 
		 Role role2 = new Role();
		 role2.setId(502);
		 role2.setName("ROLE_NORMAL");
		 
		 java.util.List<Role> rolesList =  java.util.List.of(role,role2);
		 
		 this.roleRepo.saveAll(rolesList);
		 
		 
	 }catch (Exception e) {
		// TODO: handle exception
	}
		
	}
	

}
 