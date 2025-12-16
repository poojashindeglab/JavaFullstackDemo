package com.ecommerce.project.DTO;

import java.util.List;

public class CategoryResponse {

	List<CategoryDTO> content;
	Integer pageSize;
	Integer pageNumber;
	Long totalElements;
	Integer totalPages;
	boolean islastPage;
	
	
	
	public CategoryResponse() {
		super();
	}

	public CategoryResponse(List<CategoryDTO> content, Integer pageSize, Integer pageNumber, Long totalElements,
			Integer totalPages, boolean islastPage) {
		super();
		this.content = content;
		this.pageSize = pageSize;
		this.pageNumber = pageNumber;
		this.totalElements = totalElements;
		this.totalPages = totalPages;
		this.islastPage = islastPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int i) {
		this.pageNumber = i;
	}

	public Long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(Long totalElements) {
		this.totalElements = totalElements;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	public boolean isIslastPage() {
		return islastPage;
	}

	public void setIslastPage(boolean islastPage) {
		this.islastPage = islastPage;
	}

	public CategoryResponse(List<CategoryDTO> content) {
		super();
		this.content = content;
	}

	public List<CategoryDTO> getContent() {
		return content;
	}

	public void setContent(List<CategoryDTO> content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "CategoryResponse [content=" + content + ", pageSize=" + pageSize + ", pageNumber=" + pageNumber
				+ ", totalElements=" + totalElements + ", totalPages=" + totalPages + ", islastPage=" + islastPage
				+ "]";
	}


	
}
