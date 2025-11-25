package com.jwtproject.springboot_security.controller;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.jwtproject.springboot_security.dto.JwtAuthResponse;
import com.jwtproject.springboot_security.dto.LoginDto;
import com.jwtproject.springboot_security.entity.User;
import com.jwtproject.springboot_security.service.UserService;
import com.jwtproject.springboot_security.serviceimpl.AuthServiceImpl;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private AuthServiceImpl authService;

	public AuthController(AuthServiceImpl authService) {
		super();
		this.authService = authService;
	}

	@Autowired
	private UserService userService;

	/**
	 * This endpoint is used to add user with aadhaar and pan images
	 * 
	 * @param user
	 * @param aadhaarImage
	 * @param panImage
	 * @return
	 */
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<User> addUser(@RequestPart("user") @Validated User user,
			@RequestPart("aadhaarImage") MultipartFile aadhaarImage, @RequestPart("panImage") MultipartFile panImage) {
		return ResponseEntity.ok(userService.addUser(user, aadhaarImage, panImage));
	}


	/**
	 * This endpoint is used to add user without images
	 * 
	 * @param user
	 * @return
	 */
	@PutMapping("/updateuser/{userName}")
	public void updateUser(@PathVariable String userName, @RequestBody User user) {
		userService.updateUser(user, userName);
	}

	/**
	 * This endpoint is used to get the user details with aadhaar and pan images
	 * 
	 * @param userName
	 * @return
	 */
	@GetMapping("/getuser/{userName}")
	public ResponseEntity<Map<String, Object>> getUser(@PathVariable String userName) {
		User dbUser = userService.findUser(userName);
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("user", dbUser);
		// Decode aadhaar and pan images to Base64 string for response
		response.put("aadhaarImage",
				userService.imagedecode(Base64.getEncoder().encodeToString(dbUser.getAadhaarImage()), "aadhaar.jpg"));
        response.put("panImage", userService.imagedecode(Base64.getEncoder().encodeToString(dbUser.getPanImage()), "pan.jpg"));
		return ResponseEntity.ok(response);	}

	/**
	 * This endpoint is used to login the user
	 * 
	 * @param loginDto
	 * @return
	 */
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto) {
		String token = authService.login(loginDto);

		JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
		jwtAuthResponse.setAccessToken(token);

		return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
	}

	/*
	 * * This endpoint is used to download pan image
	 * 
	 * @param userName
	 * 
	 * @return
	 */
	@GetMapping("/pan/{userName}")
	public ResponseEntity<byte[]> getPanImage(@PathVariable String userName) {
		User user = userService.findUser(userName);
		System.out.println("user.getPanImage(): " + user.getPanImage());
		if (user != null && user.getPanImage() != null) {
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=pan.jpg")
					.contentType(MediaType.IMAGE_JPEG).body(user.getPanImage());
		}
		return ResponseEntity.notFound().build();
	}

	/*
	 * * This endpoint is used to download aadhaar image
	 * 
	 * @param userName
	 * 
	 * @return
	 */
	@GetMapping("/aadhaar")
	@ResponseBody
	public ResponseEntity<byte[]> getAadhaarImage(@RequestParam String userName) {
		User user = userService.findUser(userName);
		System.out.println("user.getAadhaarImage(): " + user.getAadhaarImage());
		if (user != null && user.getAadhaarImage() != null) {
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=aadhaar.jpg")
					.contentType(MediaType.IMAGE_JPEG).body(user.getAadhaarImage());
		}
		return ResponseEntity.notFound().build();
	}
}