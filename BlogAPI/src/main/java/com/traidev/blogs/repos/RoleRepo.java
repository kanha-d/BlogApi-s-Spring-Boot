package com.traidev.blogs.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.traidev.blogs.entities.Role;

public interface RoleRepo extends JpaRepository<Role,Integer> {

}
