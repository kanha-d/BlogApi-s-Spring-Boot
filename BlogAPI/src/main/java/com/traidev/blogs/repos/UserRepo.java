package com.traidev.blogs.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.traidev.blogs.entities.User;

public interface UserRepo extends JpaRepository<User, Integer> {
	 
	
	Optional<User> findByEmail(String email);


}
