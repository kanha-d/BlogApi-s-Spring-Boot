package com.traidev.blogs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.traidev.blogs.payloads.ApiResponse;
import com.traidev.blogs.payloads.CommentDto;
import com.traidev.blogs.services.CommentService;

@RestController
@RequestMapping("/api/")
public class CommentController {
	
	
	@Autowired
	private CommentService commentService;
	
	
	@PostMapping("/comment/post/{postId}")
	public ResponseEntity<CommentDto> addComment(@RequestBody CommentDto commentDto,@PathVariable Integer postId){
		
		CommentDto comment = this.commentService.createComment(commentDto, postId);
		
		return new ResponseEntity<>(comment,HttpStatus.OK);
		
	}
	

	@GetMapping("/comment/delete/{commentId}")
	public ResponseEntity<ApiResponse> addComment(@PathVariable Integer commentId){
		
		 this.commentService.deleteComment(commentId);
		
		 return new ResponseEntity<>(new ApiResponse("Comment has been Deleted Succefully",true),HttpStatus.OK);
		
	}
	
}
