package com.ecommerce.project.controller;

import org.springframework.http.HttpHeaders;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.ecommerce.project.model.User;
import com.ecommerce.project.security.jwt.JwtUtils;
import com.ecommerce.project.security.request.LoginRequest;
import com.ecommerce.project.security.request.SignupRequest;
import com.ecommerce.project.security.response.MessageResponse;
import com.ecommerce.project.security.response.UserInfoResponse;
import com.ecommerce.project.security.service.UserDetailsImpl;
import com.ecommerce.project.service.AuthServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	AuthServiceImpl authService;
	
	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
	    try {
	    	System.out.println("Username: " + loginRequest.getUsername());
	    	System.out.println("Password: " + loginRequest.getPassword());
	        UserInfoResponse token = authService.login(loginRequest);
	        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, token.getJwtToken()).body(token);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                .body("Authentication Failed: " + e.getMessage());
	    }
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest, BindingResult result) {
		if(result.hasErrors()) {
			List<String> errors = result.getAllErrors().stream()
					.map(error -> error.getDefaultMessage()).toList();
			
			return ResponseEntity.badRequest().body(errors);
		}
		User user = authService.registerUser(signUpRequest);
		
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@GetMapping("username")
	public ResponseEntity<?> getUserName(Authentication authentication){
		if(authentication != null){
			return ResponseEntity.ok().body(authentication.getName());
		}
		else {
			return ResponseEntity.ok().body("Unable to fetch current user");
		}
	}

	@GetMapping("/user")
	public ResponseEntity<?> getUser(Authentication authentication){
		if(authentication != null) {
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		UserInfoResponse response = new UserInfoResponse();
		response.setId(userDetails.getId());
		response.setUsername(userDetails.getUsername());
		response.setRoles(roles);
		return ResponseEntity.ok().body(response);
		}else {
			return ResponseEntity.ok().body("Unable to fetch current user");
			
		}
		
	}
	
	@PostMapping("/signout")
	public ResponseEntity<?> signoutUser(){
		ResponseCookie cookie = jwtUtils.getCleanCookie();
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(new MessageResponse("User has been signed out successfully!!"));
		
	}
}
