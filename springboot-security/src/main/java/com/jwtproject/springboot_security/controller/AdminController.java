package com.jwtproject.springboot_security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwtproject.springboot_security.dto.CategoryResponse;
import com.jwtproject.springboot_security.service.EcommerceClient;

@RestController
@RequestMapping("/api/")
public class AdminController {

	@Autowired
	EcommerceClient client;
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<String> helloAdmin(){
    	return ResponseEntity.ok("Hello User");
    	
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    public ResponseEntity<CategoryResponse> helloUser(){
    	ResponseEntity<CategoryResponse> response = client.getCategories();
        return response;
    }
}