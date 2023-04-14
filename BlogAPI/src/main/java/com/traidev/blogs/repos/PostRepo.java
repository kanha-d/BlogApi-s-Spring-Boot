package com.traidev.blogs.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.traidev.blogs.entities.Category;
import com.traidev.blogs.entities.Post;
import com.traidev.blogs.entities.User;

public interface PostRepo extends JpaRepository<Post,Integer> {
	
	List<Post> findByUser(User user);
	List<Post> findByCategory(Category cat);
	
	List<Post> findByTitleContaining(String title);
	
	
	

}
