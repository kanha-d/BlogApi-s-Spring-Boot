package com.traidev.blogs.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.traidev.blogs.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment,Integer>{
	

}
