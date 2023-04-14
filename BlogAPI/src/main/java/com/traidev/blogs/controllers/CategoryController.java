package com.traidev.blogs.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.traidev.blogs.payloads.ApiResponse;
import com.traidev.blogs.payloads.CategoryDto;
import com.traidev.blogs.services.CategoryService;

@RestController
@RequestMapping("/api/cat")
public class CategoryController {
	
	@Autowired
	private CategoryService catService;
	
	
	//Create User
		@PostMapping("/")
		public ResponseEntity<CategoryDto> createCat(@RequestBody CategoryDto catDto)
		{
			CategoryDto createCategoryDto = this.catService.createCat(catDto);
			return new ResponseEntity<>(createCategoryDto,HttpStatus.CREATED);
	 	}
		
		//Update User
		@PutMapping("/{catId}")
		public ResponseEntity<CategoryDto> updateCat(@RequestBody CategoryDto catDto,@PathVariable("catId") Integer catId)
		{
			
			CategoryDto updatedCat = this.catService.updateCat(catDto, catId);
			
			return  ResponseEntity.ok(updatedCat);
			
		}
		
		//Delete User
		@DeleteMapping("/{catId}")
		public ResponseEntity<ApiResponse> deleteCat(@PathVariable("catId") Integer cid)
		{
			this.catService.delCat(cid);
			return new ResponseEntity<ApiResponse>(new ApiResponse("Category Deleted Succefully",true),HttpStatus.OK);
			
		}
		
		//Get user by id
		@GetMapping("/{catId}")
		public ResponseEntity<CategoryDto> getCat(@PathVariable("catId") Integer catId)
		{
			
			CategoryDto catData = this.catService.getById(catId);
			
			return  ResponseEntity.ok(catData);
			
		}
		
		//Get all users
		@GetMapping("/")
		public ResponseEntity<List<CategoryDto>> getAllCats()
		{
			
			List<CategoryDto> catList = this.catService.getAllCategory();
			
			return  ResponseEntity.ok(catList);
			
		}
		
		
		

}
