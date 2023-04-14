package com.traidev.blogs.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.traidev.blogs.entities.Category;
import com.traidev.blogs.entities.Comment;
import com.traidev.blogs.entities.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostDto {
	
	
	private Integer postId;
	
	private String title;
	
	private String content;
	
	private String imgName;
	
	private Date addedDate;
	
	private CategoryDto category;
	
	private UserDto user;
	
	private Set<CommentDto> comments = new HashSet<>();
	

}
