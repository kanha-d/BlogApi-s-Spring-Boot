package com.traidev.blogs.services.imp;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.traidev.blogs.entities.Comment;
import com.traidev.blogs.entities.Post;
import com.traidev.blogs.exceptions.ResourceNotFoundException;
import com.traidev.blogs.payloads.CommentDto;
import com.traidev.blogs.repos.CommentRepo;
import com.traidev.blogs.repos.PostRepo;
import com.traidev.blogs.services.CommentService;


@Service
public class CommentServiceImpl implements CommentService {

	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		
		Post post  = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Post not found with this Id",postId));
		
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		
		return this.modelMapper.map(this.commentRepo.save(comment), CommentDto.class);
		
		
	}

	@Override
	public void deleteComment(Integer commentId) {
		
		Comment comment  = this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","Comment not found with this Id",commentId));

		this.commentRepo.delete(comment);
		
	}

}
