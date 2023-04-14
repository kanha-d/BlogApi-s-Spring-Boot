package com.traidev.blogs.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.traidev.blogs.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>  {

}
