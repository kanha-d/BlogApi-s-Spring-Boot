package com.traidev.blogs.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="posts")
@NoArgsConstructor
@Getter
@Setter
public class Post {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer postId;
	
	private String title;
	
	@Column(length=10000)
	private String content;
	private String imgName;
	private Date addedDate;
	
	@OneToOne
	@JoinColumn(name = "category_id")
	private Category category;
		
	@ManyToOne
	private User user;
	
	
	@OneToMany(mappedBy = "post",cascade=CascadeType.ALL)
	private Set<Comment> comments = new HashSet<>();
	
}
