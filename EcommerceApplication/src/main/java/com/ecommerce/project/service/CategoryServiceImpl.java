package com.ecommerce.project.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ecommerce.project.DTO.CategoryDTO;
import com.ecommerce.project.DTO.CategoryResponse;
import com.ecommerce.project.Exception.APIException;
import com.ecommerce.project.Exception.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public CategoryDTO addCategory(CategoryDTO categoryDto) {
		Category category = modelMapper.map(categoryDto, Category.class);
		Category getCategory = categoryRepository.findByCategoryName(category.getCategoryName());
		if (getCategory != null) {
			throw new APIException("Category with name " + category.getCategoryName() + " already exists");
		}
		return modelMapper.map(categoryRepository.save(category), CategoryDTO.class);
	}

	@Override
	public CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDto) {
		
	 categoryRepository.findById(categoryId)
				.orElseThrow(() ->
		new ResourceNotFoundException("category","categoryId", categoryId));
				
		categoryDto.setCategoryId(categoryId);
		Category updateCat = modelMapper.map(categoryDto, Category.class);
		return modelMapper.map(categoryRepository.save(updateCat), CategoryDTO.class);
	}

	@Override
	public String deleteCategory(Long categoryId) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category","categoryId", categoryId));
		categoryRepository.delete(category);
		return "Category with the categoryId: "+ categoryId +" deleted successfully";
	}
	@Override
	public CategoryResponse getCategory(Integer pageNumber, Integer pageSize, String sortOrder, String sortBy) {
		Sort sortOrderObj = sortOrder.equalsIgnoreCase("asc")? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		Pageable pager = PageRequest.of(pageNumber, pageSize, sortOrderObj);
		Page<Category> pageCategories = categoryRepository.findAll(pager);
		List<Category> categories = pageCategories.getContent();
		if (categories.isEmpty()) {
			throw new APIException("No Categories found");
		}
		List<CategoryDTO> categoryDtos = categories.stream().map(cat -> modelMapper.map(cat, CategoryDTO.class)).toList();
		CategoryResponse response = new CategoryResponse(categoryDtos);
		response.setPageNumber(pageCategories.getNumber());
		response.setPageSize(pageCategories.getSize());
		response.setTotalElements(pageCategories.getTotalElements());
		response.setTotalPages(pageCategories.getTotalPages());
		response.setIslastPage(pageCategories.isLast());
		return response;
	}
}
