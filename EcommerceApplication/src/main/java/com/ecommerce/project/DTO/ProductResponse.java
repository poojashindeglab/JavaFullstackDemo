package com.ecommerce.project.DTO;

import java.util.List;

public class ProductResponse {

	List<ProductDTO> products;

	public List<ProductDTO> getProducts() {
		return products;
	}

	public void setProducts(List<ProductDTO> products) {
		this.products = products;
	}

	
	public ProductResponse() {
		super();
	}

	public ProductResponse(List<ProductDTO> products) {
		super();
		this.products = products;
	}
	
}	
