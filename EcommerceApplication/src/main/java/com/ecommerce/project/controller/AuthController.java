package com.ecommerce.project.controller;

import java.net.http.HttpHeaders;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.project.Exception.ResourceNotFoundException;
import com.ecommerce.project.model.User;
import com.ecommerce.project.security.request.LoginRequest;
import com.ecommerce.project.security.request.SignupRequest;
import com.ecommerce.project.security.response.MessageResponse;
import com.ecommerce.project.security.response.UserInfoResponse;
import com.ecommerce.project.service.AuthServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	AuthServiceImpl authService;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
	    try {
	    	System.out.println("Username: " + loginRequest.getUsername());
	    	System.out.println("Password: " + loginRequest.getPassword());
	        UserInfoResponse token = authService.login(loginRequest);
	        return ResponseEntity.ok().header(org.springframework.http.HttpHeaders.SET_COOKIE, token.getJwtToken()).body(token);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                .body("Authentication Failed: " + e.getMessage());
	    }
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		User user = authService.registerUser(signUpRequest);

		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

}
