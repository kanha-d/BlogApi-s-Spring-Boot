package com.traidev.blogs.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.traidev.blogs.entities.Post;
import com.traidev.blogs.payloads.ApiResponse;
import com.traidev.blogs.payloads.PostDto;
import com.traidev.blogs.payloads.PostResponse;
import com.traidev.blogs.services.FileService;
import com.traidev.blogs.services.PostService;


@RestController
@RequestMapping("/api/")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,@PathVariable Integer userId,@PathVariable Integer categoryId){
		
		PostDto createdPost = this.postService.createPost(postDto, userId, categoryId);
		
		return new ResponseEntity<PostDto>(createdPost,HttpStatus.CREATED);
		
 	}
	
	@PutMapping("/post/{postId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable Integer postId,@PathVariable Integer categoryId){
		
		PostDto updatedPost = this.postService.updatePost(postDto, postId, categoryId);
		
		return new ResponseEntity<PostDto>(updatedPost,HttpStatus.CREATED);
		
 	}
	
	

	//Get by User
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostbyUser(@PathVariable Integer userId){
		
		List<PostDto> posts = this.postService.getPostbyUser(userId);
		
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
		
	}
	
	@GetMapping("/cat/{catId}/posts")
	public ResponseEntity<List<PostDto>> getPostbyCat(@PathVariable Integer catId){
		
		List<PostDto> posts = this.postService.getPostbyCategory(catId);
		
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
		
	}
	
	
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value="pageNumber",defaultValue="0",required=false) Integer pageNo,
			@RequestParam(value="pageSize",defaultValue="5",required=false) Integer pageSize,
			@RequestParam(value="sortBy",defaultValue="postId",required=false) String sortBy
			) {
		
		PostResponse posts = this.postService.getAllPosts(pageSize,pageNo,sortBy);
		
		return new ResponseEntity<PostResponse>(posts,HttpStatus.OK);
		
	}
	
	//Delete Post
	@DeleteMapping("/delete/{postId}")
	public ResponseEntity<ApiResponse> deleteCat(@PathVariable("postId") Integer post_id)
	{
		this.postService.deletePost(post_id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Post Deleted Succefully",true),HttpStatus.OK);
		
	}
	
	@GetMapping("/post/{postId}")
	public ResponseEntity<PostDto> getPost(@PathVariable("postId") Integer post_id)
	{
		PostDto post = this.postService.getPostById(post_id);
		
		return new ResponseEntity<PostDto>(post,HttpStatus.OK);
		
	}
	
	
	@GetMapping("/post/search/{keyword}")
	public ResponseEntity<List<PostDto>> getPost(@PathVariable("keyword") String keyword)
	{
		List<PostDto> posts = this.postService.searchPosts(keyword);
		
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
		
	}
	
	
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(
			@RequestParam("file") MultipartFile file,
			@PathVariable Integer postId) throws IOException{
		
		PostDto postDto = this.postService.getPostById(postId);
		
		
		String filename = this.fileService.uploadImage(path, file);
		
		postDto.setImgName(filename);
		
		PostDto updatedPost = this.postService.updatePost(postDto, postId);
		
		return new ResponseEntity<PostDto>(updatedPost,HttpStatus.OK);
		
		
	}
	
	
	//Method to serve images
	@GetMapping(value="/post/image/{fileName}",produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(
			@PathVariable("fileName") String fileName,
			HttpServletResponse response
			) throws IOException {
		
 		InputStream resource = this.fileService.getResource(path, fileName);
		
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		
		StreamUtils.copy(resource, response.getOutputStream());
		
		
	}
	
	
	
	
	
	
}
