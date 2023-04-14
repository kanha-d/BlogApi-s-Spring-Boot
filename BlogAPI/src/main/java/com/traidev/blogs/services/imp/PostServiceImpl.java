package com.traidev.blogs.services.imp;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.traidev.blogs.entities.Category;
import com.traidev.blogs.entities.Post;
import com.traidev.blogs.entities.User;
import com.traidev.blogs.exceptions.ResourceNotFoundException;
import com.traidev.blogs.payloads.PostDto;
import com.traidev.blogs.payloads.PostResponse;
import com.traidev.blogs.repos.CategoryRepo;
import com.traidev.blogs.repos.PostRepo;
import com.traidev.blogs.repos.UserRepo;
import com.traidev.blogs.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private ModelMapper modealMapper;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer catId) {

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User id Not Found", userId));
		Category category = this.categoryRepo.findById(catId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Not Found!", catId));

		Post post = this.modealMapper.map(postDto, Post.class);
		post.setImgName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);

		Post newPost = this.postRepo.save(post);

		return this.modealMapper.map(newPost, PostDto.class);

	}
	
	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		
		Post post  = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Post not found with this Id",postId));
		
		post.setTitle(postDto.getTitle());
		post.setImgName(postDto.getImgName());
		post.setContent(postDto.getContent());
		
		Post updatedPost = this.postRepo.save(post);
		
		return this.modealMapper.map(updatedPost, PostDto.class);

	}	
	
	

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId,Integer catId) {
		
		Category cat  = this.categoryRepo.findById(catId).orElseThrow(()->new ResourceNotFoundException("Category","Category not found with this Id",postId));
		
		Post post  = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Post not found with this Id",postId));
		
		post.setTitle(postDto.getTitle());
		post.setImgName(postDto.getImgName());
		post.setContent(postDto.getContent());
		post.setCategory(cat);
		
		Post updatedPost = this.postRepo.save(post);
		
		return this.modealMapper.map(updatedPost, PostDto.class);

	}	
	
	

	@Override
	public void deletePost(Integer postId) {
		Post post  = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Post not found with this Id",postId));
		this.postRepo.delete(post);

	}

	@Override
	public PostResponse getAllPosts(Integer pageSize,Integer pageNo,String sortBy) {
		
		Pageable page  = PageRequest.of(pageNo,pageSize,Sort.by(sortBy));
		
		Page<Post> listPages = this.postRepo.findAll(page);
		
		List<Post> posts = listPages.getContent();
		
		List<PostDto> postDtos = posts.stream().map(post->this.modealMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(listPages.getNumber());
		postResponse.setPageSize(listPages.getSize());
		postResponse.setTotalElements(listPages.getTotalElements());
		postResponse.setTotalPages(listPages.getTotalPages());
		postResponse.setLastPage(listPages.isLast());
		
		return postResponse;
		
		
		
	}

	@Override
	public PostDto getPostById(Integer postId) {
		
		
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Post not found with this Id",postId));
		
		return this.modealMapper.map(post, PostDto.class);
		
	}

	@Override
	public List<PostDto> getPostbyCategory(Integer catId) {
		
		
		Category cat = this.categoryRepo.findById(catId).orElseThrow(()->new ResourceNotFoundException("Category","Category not found with this Id",catId));
		
		List<Post> posts = this.postRepo.findByCategory(cat);
		
		List<PostDto> postDtos = posts.stream().map(post->this.modealMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return postDtos;
		
		
	}

	@Override
	public List<PostDto> getPostbyUser(Integer userId) {

		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","User not found with this Id",userId));
		
		List<Post> posts = this.postRepo.findByUser(user);
		
		List<PostDto> postDtos = posts.stream().map(post->this.modealMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return postDtos;
	}

	
	@Override
	public List<PostDto> searchPosts(String keyword) {
		
		List<Post> findByTitleLike = this.postRepo.findByTitleContaining(keyword);
		
		List<PostDto> postDtos = findByTitleLike.stream().map(post->this.modealMapper.map(post, PostDto.class)).collect(Collectors.toList());
			
		return postDtos;
		
	}

}
