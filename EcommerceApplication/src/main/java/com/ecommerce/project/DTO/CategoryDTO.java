package com.ecommerce.project.DTO;

public class CategoryDTO {

	String categoryName;
	Long categoryId;
	
	
	public CategoryDTO() {
		super();
	}

	public CategoryDTO(String categoryName, Long categoryId) {
		super();
		this.categoryName = categoryName;
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	@Override
	public String toString() {
		return "CategoryDTO [categoryName=" + categoryName + ", categoryId=" + categoryId + "]";
	}
	
}
