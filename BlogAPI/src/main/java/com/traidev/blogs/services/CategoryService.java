package com.traidev.blogs.services;

import java.util.List;

import com.traidev.blogs.payloads.CategoryDto;
import com.traidev.blogs.payloads.UserDto;



public interface CategoryService {
	
	CategoryDto createCat(CategoryDto cat);
	CategoryDto updateCat(CategoryDto cat,Integer catId);
	List<CategoryDto> getAllCategory();
	CategoryDto getById(Integer catId);
	void delCat(Integer catId);
	

}
