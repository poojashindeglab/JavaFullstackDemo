package com.ecommerce.project.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.project.DTO.ProductDTO;
import com.ecommerce.project.DTO.ProductResponse;

public interface ProductService {

	ProductDTO createProduct(ProductDTO productDTO, Long categoryId);

	ProductResponse getAllProducts();

	ProductResponse getByCategory(Long categoryId);

	ProductResponse searchProductsByKeyword(String keyword);

	ProductResponse updateProduct(Long productId, ProductDTO productDTO);

	void deleteProduct(Long productId);

	ProductDTO updateImage(Long productId, MultipartFile file) throws IOException;

}
