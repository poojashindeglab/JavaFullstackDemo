package com.ecommerce.project.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.project.DTO.ProductDTO;
import com.ecommerce.project.DTO.ProductResponse;
import com.ecommerce.project.Exception.APIException;
import com.ecommerce.project.Exception.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.repository.CategoryRepository;
import com.ecommerce.project.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	FileService fileService;
	@Value("${product.image.upload.path}")
	private String imageUploadPath;
	
	@Override
	public ProductDTO createProduct(ProductDTO productDTO, Long categoryId) {
		Product dbProduct = productRepository.findByProductNameIgnoreCase(productDTO.getProductName());
		if (dbProduct != null) {
			throw new APIException("Product with the productName: " + productDTO.getProductName() + "is already exists!!");
		}
		Product product = modelMapper.map(productDTO, Product.class);
		
		
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException("Category" , "categoryId" , categoryId));
		product.setCategory(category);
		product.setImage("default.png");
		Double specialPrice = product.getPrice() - (product.getPrice() * product.getDiscount()/100);
		product.setSpecialPrice(specialPrice);
		ProductDTO savedProduct = modelMapper.map(productRepository.save(product), ProductDTO.class) ;
		return savedProduct;
	}

	@Override
	public ProductResponse getAllProducts() {
		List<Product> products = productRepository.findAll();
		if (products.isEmpty()) {
			throw new APIException("Products are not available!!");
		}
		List<ProductDTO> productDTOs = products.stream()
				.map(product -> modelMapper.map(product, ProductDTO.class))
				.collect(Collectors.toList());
		ProductResponse productResponse = new ProductResponse();
		productResponse.setProducts(productDTOs);
		return productResponse;
	}

	@Override
	public ProductResponse getByCategory(Long categoryId) {
		Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category" , "categoryId" , categoryId));
		List<Product> products = productRepository.findByCategoryOrderByPriceAsc(category);
		if (products.isEmpty()) {
			throw new ResourceNotFoundException("Products", "categoryId", categoryId);
		}
		List<ProductDTO> productDTOs = products.stream().map(product -> modelMapper.map(product, ProductDTO.class))
				.collect(Collectors.toList());
		ProductResponse productResponse = new ProductResponse();
		productResponse.setProducts(productDTOs);
		return productResponse;
	}

	@Override
	public  ProductResponse searchProductsByKeyword(String keyword) {
		List<Product> products = productRepository.findByProductNameLikeIgnoreCase("%" + keyword + "%");
		if (products.isEmpty()) {
			throw new ResourceNotFoundException("Products", "keyword", keyword);
		}
		List<ProductDTO> productDTOs = products.stream().map(product -> modelMapper.map(product, ProductDTO.class))
				.collect(Collectors.toList());
		ProductResponse productResponse = new ProductResponse();
		productResponse.setProducts(productDTOs);
		return productResponse;
	}

	@Override
	public ProductResponse updateProduct(Long productId, ProductDTO productDTO) {
		Product dbProduct = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
		dbProduct.setProductName(productDTO.getProductName());
		dbProduct.setDescription(productDTO.getDescription());
		dbProduct.setPrice(productDTO.getPrice());
		dbProduct.setDiscount(productDTO.getDiscount());
		Double specialPrice = productDTO.getPrice() - (productDTO.getPrice() * productDTO.getDiscount()/100);
		dbProduct.setSpecialPrice(specialPrice);
		ProductDTO updatedProductDTO = modelMapper.map(productRepository.save(dbProduct), ProductDTO.class);
		ProductResponse productResponse = new ProductResponse();
		productResponse.setProducts(List.of(updatedProductDTO));
		return productResponse;
	}

	@Override
	public void deleteProduct(Long productId) {
        Product dbProduct = productRepository.findById(productId)
        		.orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
        productRepository.delete(dbProduct);
	}

	@Override
	public ProductDTO updateImage(Long productId, MultipartFile file) throws IOException {
		Product dbProduct = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product","productId", productId));
		String fileName = fileService.uploadImage(imageUploadPath, file);
		dbProduct.setImage(fileName);
		
		return modelMapper.map(productRepository.save(dbProduct), ProductDTO.class);
	}



}
