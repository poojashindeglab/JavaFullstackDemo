package com.ecommerce.project.DTO;

import java.util.ArrayList;
import java.util.List;

import com.ecommerce.project.model.Product;

public class CartDTO {

	private Long cartId;
	private Double totalPrice = 0.0;
	private List<ProductDTO> products = new ArrayList<>();
	
	public Long getCartId() {
		return cartId;
	}

	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<ProductDTO> getProducts() {
		return products;
	}

	public void setProducts(List<ProductDTO> products) {
		this.products = products;
	}

	
	public CartDTO() {
		super();
	}

	public CartDTO(Long cartId, Double totalPrice, List<ProductDTO> products) {
		super();
		this.cartId = cartId;
		this.totalPrice = totalPrice;
		this.products = products;
	}

	@Override
	public String toString() {
		return "CartDTO [cartId=" + cartId + ", totalPrice=" + totalPrice + ", products=" + products + "]";
	}
	
	
}
