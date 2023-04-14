package com.traidev.blogs.services.imp;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.traidev.blogs.entities.Category;
import com.traidev.blogs.exceptions.ResourceNotFoundException;
import com.traidev.blogs.payloads.CategoryDto;
import com.traidev.blogs.repos.CategoryRepo;
import com.traidev.blogs.services.CategoryService;


@Service
public class CategoryServiceImpl implements CategoryService {

	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoryDto createCat(CategoryDto catdto) {
		
		Category cat = this.modelMapper.map(catdto, Category.class);
		Category added = this.categoryRepo.save(cat);
		
		return this.modelMapper.map(added, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCat(CategoryDto catdto, Integer catId) {
		
		
		Category cat = this.categoryRepo.findById(catId).orElseThrow(()->new ResourceNotFoundException("Category","Category Id",0));
				
		
		cat.setCatTitle(catdto.getCatTitle());
		cat.setCatDesc(catdto.getCatDesc());
		
		Category updated = this.categoryRepo.save(cat);
		
		return this.modelMapper.map(updated, CategoryDto.class);
		
		
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		 

		List<Category> cats = this.categoryRepo.findAll();
		List<CategoryDto> catDtos = cats.stream().map(cat->this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
		
		return catDtos;
	}

	@Override
	public CategoryDto getById(Integer catId) {
		
		Category cat = this.categoryRepo.findById(catId).orElseThrow(()->new ResourceNotFoundException("Category","Category Id",0));

		return this.modelMapper.map(cat, CategoryDto.class);
		
	}

	@Override
	public void delCat(Integer catId) {
		
		Category cat = this.categoryRepo.findById(catId).orElseThrow(()->new ResourceNotFoundException("Category","Category Id",0));
		this.categoryRepo.delete(cat);

	}
	
	

}
