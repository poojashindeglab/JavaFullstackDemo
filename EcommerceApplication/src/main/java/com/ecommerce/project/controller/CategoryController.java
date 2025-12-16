package com.ecommerce.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ecommerce.project.DTO.CategoryDTO;
import com.ecommerce.project.DTO.CategoryResponse;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.service.CategoryService;
import com.ecommerce.project.utils.AppConstant;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class CategoryController {

	CategoryService categoryService;
	
	@Autowired
	CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@GetMapping("/public/categories")
	public ResponseEntity<CategoryResponse> getCategories(@RequestParam (name = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false)Integer pageNumber,
		@RequestParam(name="pageSize", defaultValue = AppConstant.PAGE_SIZE,  required = false) Integer pageSize,
		@RequestParam(name="sortOrder", defaultValue = AppConstant.SORT_ORDER,  required = false) String sortOrder, 
		@RequestParam(name="sortBy", defaultValue = AppConstant.SORT_CATEGORY_BY,  required = false) String sortBy){
		CategoryResponse categories = categoryService.getCategory(pageNumber, pageSize, sortOrder, sortBy);
		return new ResponseEntity<CategoryResponse>(categories, HttpStatus.OK);
	}
	
	@PostMapping("/public/categories")
	public ResponseEntity<CategoryDTO> addCategory(@Valid @RequestBody CategoryDTO categoryDto) {
		 CategoryDTO catDto = categoryService.addCategory(categoryDto);
		return new ResponseEntity<CategoryDTO>(catDto, HttpStatus.CREATED);
	}
	
	@PutMapping("/public/categories/{categoryId}")
	public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long categoryId, @RequestBody CategoryDTO categoryDto) {
		CategoryDTO updateCategory=categoryService.updateCategory(categoryId, categoryDto);
		return  new ResponseEntity<>(updateCategory, HttpStatus.OK);
	}
	
	@DeleteMapping("/public/categories/{categoryId}")
	public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
            String result = categoryService.deleteCategory(categoryId);
            return new ResponseEntity<>(result, HttpStatus.OK);

	}
}
