package com.ecommerce.project.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.project.DTO.ProductDTO;
import com.ecommerce.project.DTO.ProductResponse;
import com.ecommerce.project.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class ProductController {

	@Autowired
	ProductService productService;
	
	@PostMapping("/admin/categories/{categoryId}/product")
	public ResponseEntity<ProductDTO> createProduct(@Valid
			@RequestBody ProductDTO productDTO, @PathVariable Long categoryId) {
		ProductDTO createdProduct = productService.createProduct(productDTO, categoryId);
		return  new ResponseEntity<ProductDTO>(createdProduct, HttpStatus.CREATED);
	}
	
	@GetMapping("/public/products")
	public ResponseEntity<ProductResponse> getAllProducts() {
		ProductResponse products = productService.getAllProducts(); 
		return new ResponseEntity<ProductResponse>(products, HttpStatus.OK);
	}
	
	@GetMapping("/public/categories/{categoryId}/products")
	public ResponseEntity<ProductResponse> getProductByCategoryId(@PathVariable Long categoryId) {
		ProductResponse products = productService.getByCategory(categoryId);
		return new ResponseEntity<ProductResponse>(products,HttpStatus.OK);
	}
	
	@GetMapping("/public/categories/keyword/{keyword}")
	public ResponseEntity<ProductResponse> searchProductsByKeyword(@PathVariable String keyword) {
		ProductResponse products = productService.searchProductsByKeyword(keyword);
		return new ResponseEntity<ProductResponse>(products, HttpStatus.OK);
	}
	
	@PutMapping("/admin/products/{productId}")
	public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long productId, @RequestBody ProductDTO productDTO) {
		ProductResponse products = productService.updateProduct(productId, productDTO);
		return new ResponseEntity<ProductResponse>(products, HttpStatus.OK); // 
		
	}
	
	@DeleteMapping("/admin/products/{productId}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
		productService.deleteProduct(productId);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	@PutMapping("/products/{productId}/image")
	public ResponseEntity<ProductDTO> uploadProductImage(@PathVariable Long productId, @RequestParam(name="image") MultipartFile file) throws IOException {
		ProductDTO productDTO = productService.updateImage(productId, file);
		return new ResponseEntity<ProductDTO>(productDTO,HttpStatus.OK);
	}
	
}
