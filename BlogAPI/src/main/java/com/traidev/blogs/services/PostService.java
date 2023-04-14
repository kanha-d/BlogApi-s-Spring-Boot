package com.traidev.blogs.services;

import java.util.List;

import com.traidev.blogs.entities.Post;
import com.traidev.blogs.payloads.PostDto;
import com.traidev.blogs.payloads.PostResponse;

public interface PostService {

	
	PostDto createPost(PostDto postDto,Integer userId,Integer catId);
	
	PostDto updatePost(PostDto postDto,Integer postId);
	
	PostDto updatePost(PostDto postDto,Integer postId,Integer catId);
	
	void deletePost(Integer postId);
	
	
	PostResponse getAllPosts(Integer pageSize,Integer pageNo,String sortBy);
	
	
	PostDto getPostById(Integer postId);
	
	
	List<PostDto> getPostbyCategory(Integer catId);
	
	
	List<PostDto> getPostbyUser(Integer userId);
	
	
	List<PostDto> searchPosts(String keyword);
	
	
	
	
		
	
}
