package com.ecommerce.project.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "products")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long productId;
	
	@NotEmpty(message = "Product name cannot be null or empty")
	@Size(min = 3, message = "Product name must be at least 3 characters long")
	private String productName;
	private String description;
	
	@NotNull(message = "Price cannot be null")
	private Double price;
	
	@NotNull(message = "Quantity cannot be null")
	private Integer quantity;
	private Double specialPrice;
	private String image;
	
	@NotNull(message = "Discount cannot be null or empty")
	private Double discount;
	
	@ManyToOne
	@JoinColumn(name= "category_id")
	private Category category;

	@ManyToOne
	@JoinColumn(name = "seller_id")
	private User user;
	
	@OneToMany(mappedBy = "product" ,cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	private List<CartItem> products = new ArrayList<CartItem>();
	
	public List<CartItem> getProducts() {
		return products;
	}

	public void setProducts(List<CartItem> products) {
		this.products = products;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

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

	public void setPrice(Double  price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getSpecialPrice() {
		return specialPrice;
	}

	public void setSpecialPrice(Double specialPrice) {
		this.specialPrice = specialPrice;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	
	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Product() {
		super();
	}

	public Product(Long productId,
			@NotEmpty(message = "Product name cannot be null or empty") @Size(min = 3, message = "Product name must be at least 3 characters long") String productName,
			String description, @NotNull(message = "Price cannot be null") Double price,
			@NotNull(message = "Quantity cannot be null") Integer quantity, Double specialPrice, String image,
			@NotNull(message = "Discount cannot be null or empty") Double discount, Category category, User user,
			List<CartItem> products) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.description = description;
		this.price = price;
		this.quantity = quantity;
		this.specialPrice = specialPrice;
		this.image = image;
		this.discount = discount;
		this.category = category;
		this.user = user;
		this.products = products;
	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", productName=" + productName + ", description=" + description
				+ ", price=" + price + ", quantity=" + quantity + ", specialPrice=" + specialPrice + ", image=" + image
				+ ", discount=" + discount + ", user=" + user + ", products=" + products + "]";
	}
	

	
}
