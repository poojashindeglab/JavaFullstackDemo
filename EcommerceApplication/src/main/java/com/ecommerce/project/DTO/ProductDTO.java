package com.ecommerce.project.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ProductDTO {

	private Long productId;
	@NotEmpty(message = "Product name cannot be null or empty")
	@Size(min = 3, message = "Product name must be at least 3 characters long")	private String productName;
	private String description;
	
	@NotNull(message = "Price cannot be null")
	private Double price;
	
	@NotNull(message = "Quantity cannot be null")
	private Integer quantity;
	private String image;
	private Double specialPrice;
	
	@NotNull(message = "Discount cannot be null or empty")
	private Double discount;
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}

	public Double getSpecialPrice() {
		return specialPrice;
	}
	public void setSpecialPrice(Double specialPrice) {
		this.specialPrice = specialPrice;
	}
	
	

	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	
	public ProductDTO() {
		super();
	}

	public ProductDTO(Long productId, String productName, String description, Double price, Integer quantity,
			String image, Double specialPrice, Double discount) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.description = description;
		this.price = price;
		this.quantity = quantity;
		this.image = image;
		this.specialPrice = specialPrice;
		this.discount = discount;
	}
	
}
