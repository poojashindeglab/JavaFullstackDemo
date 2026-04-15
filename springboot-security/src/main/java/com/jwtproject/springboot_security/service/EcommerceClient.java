package com.jwtproject.springboot_security.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.jwtproject.springboot_security.dto.CategoryResponse;


@FeignClient(name = "EcommerceApplication", url ="http://localhost:8080/api")
public interface EcommerceClient {

	@GetMapping("/public/categories")
	public ResponseEntity<CategoryResponse> getCategories();
}
