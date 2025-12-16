package com.ecommerce.project.service;

import com.ecommerce.project.DTO.CategoryDTO;
import com.ecommerce.project.DTO.CategoryResponse;

public interface CategoryService {
	public CategoryDTO addCategory(CategoryDTO categoryDto);
	public CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDto);
	public String deleteCategory(Long categoryId);
	CategoryResponse getCategory(Integer pageNumber, Integer pageSize, String sortOrder, String sortBy);
}
