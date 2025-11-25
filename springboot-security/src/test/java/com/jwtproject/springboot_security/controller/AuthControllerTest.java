package com.jwtproject.springboot_security.controller;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.security.AuthenticationAuditListener;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jwtproject.springboot_security.config.JwtTokenProvider;
import com.jwtproject.springboot_security.dto.JwtAuthResponse;
import com.jwtproject.springboot_security.dto.LoginDto;
import com.jwtproject.springboot_security.entity.User;
import com.jwtproject.springboot_security.service.UserService;
import com.jwtproject.springboot_security.serviceimpl.AuthServiceImpl;
import java.nio.file.Path;


@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(AuthController.class)
@ExtendWith(SpringExtension.class)
public class AuthControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockitoBean
	private UserService serviceImpl;
	@MockitoBean
	private AuthServiceImpl authImpl;
	@MockitoBean
	private JwtTokenProvider authProvider;
	@MockitoBean
	private AuthenticationManager authenticate ;
	
	private String URI ="/api/auth";
	
	@Test
	void addUser() throws Exception {
		String image = "adharimahebyteformat";
		byte[] decodedBytes= image.getBytes(StandardCharsets.UTF_8);
		User userMap = new User();
		userMap.setUsername("userName");
		userMap.setPassword("password");
		userMap.setEmail("email");
		userMap.setAadhaarImage(decodedBytes);
		userMap.setPanImage(decodedBytes);
		
		MockMultipartFile user = new MockMultipartFile("user", "", "application/json",new ObjectMapper().writeValueAsBytes(userMap));
		MockMultipartFile aadharImage = new MockMultipartFile("aadhaarImage", decodedBytes);
		MockMultipartFile panImage = new MockMultipartFile("panImage", decodedBytes);
		
		Mockito.when(serviceImpl.addUser(userMap,aadharImage,panImage)).thenReturn(userMap);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Bearer", "authtoken");
		String inputJson = new ObjectMapper().writeValueAsString(userMap);
		mockMvc.perform(multipart(URI)
                .file(user)
                .file(aadharImage)
                .file(panImage)
                .contentType(MediaType.MULTIPART_FORM_DATA))
        .andExpect(status().isOk());
	}
	
	@Test
	void getUsertest() throws Exception {
		String image = "adharimahebyteformat";
		byte[] decodedBytes= image.getBytes(StandardCharsets.UTF_8);
		User userMap = new User();
		userMap.setUsername("userName");
		userMap.setPassword("password");
		userMap.setEmail("email");
		userMap.setAadhaarImage(decodedBytes);
		userMap.setPanImage(decodedBytes);
		

		Mockito.when(serviceImpl.findUser("userName")).thenReturn(userMap);
		String expectedBase64 = Base64.getEncoder().encodeToString(decodedBytes);
		byte[] expectedDecode = java.util.Base64.getDecoder().decode(expectedBase64);
		Mockito.when(serviceImpl.imagedecode(expectedBase64, "aadhaar.jpg")).thenReturn(java.nio.file.Files.write(java.nio.file.Paths.get("aadhaar.jpg"), expectedDecode));
		Mockito.when(serviceImpl.imagedecode(expectedBase64, "pan.jpg")).thenReturn(java.nio.file.Files.write(java.nio.file.Paths.get("pan.jpg"), expectedDecode));

		mockMvc.perform(get("/api/auth/getuser/{userName}", userMap.getUsername())
				 .header("Authorization", "Bearer token")
				 .accept(MediaType.APPLICATION_JSON))
         .andExpect(status().isOk())
         .andExpect(jsonPath("$.user.email").value(userMap.getEmail()))
         .andExpect(jsonPath("$.user.password").value(userMap.getPassword()))
         .andExpect(jsonPath("$.panImage", is("file:///C:/Users/PoojaShinde/eclipse-workspace/SpringBootProject/springboot-security/pan.jpg")))
			.andExpect(jsonPath("$.aadhaarImage", is("file:///C:/Users/PoojaShinde/eclipse-workspace/SpringBootProject/springboot-security/aadhaar.jpg")));
	}
	
	@Test
	void login() throws Exception {
		
		LoginDto dto = new LoginDto();
		dto.setUsernameOrEmail("Pooja Shinde12323@gmail.com");
		dto.setPassword("admin123@123234");
		
		String token = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJQb29qYSBTaGluZGUxMjMyM0BnbWFpbC5jb20iLCJpYXQiOjE3NTgyNjU3MzQsImV4cCI6MTc1ODg3MDUzNH0.oDj96K3_yDzJNIbEJIEehbQpJYDBmn1MHp1jvYsv-DIkDRbTq1As54H6wi3liZIr";
		Mockito.when(authImpl.login(dto)).thenReturn(token);
		AuthenticationManager manager = new AuthenticationManager() {
			
			@Override
			public Authentication authenticate(Authentication authentication) throws AuthenticationException {
				// TODO Auto-generated method stub
				return null;
			}
		};
		org.springframework.security.core.Authentication auth = manager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsernameOrEmail(), dto.getPassword()));
		Mockito.when(authenticate.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsernameOrEmail(), dto.getPassword()))).thenReturn(auth);
		JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
		jwtAuthResponse.setAccessToken("eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJQb29qYSBTaGluZGUxMjMyM0BnbWFpbC5jb20iLCJpYXQiOjE3NTgyNjU3MzQsImV4cCI6MTc1ODg3MDUzNH0.oDj96K3_yDzJNIbEJIEehbQpJYDBmn1MHp1jvYsv-DIkDRbTq1As54H6wi3liZIr");
		
		String inputJson = new ObjectMapper().writeValueAsString(dto);
		String expectedOutput = new ObjectMapper().writeValueAsString(jwtAuthResponse);

		MvcResult result = mockMvc.perform(post(URI + "/login").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(inputJson))
		.andExpect(status().isOk())
		.andReturn();

		String returnedResponse = result.getResponse().getContentAsString();
		System.out.println("Login Response: " + returnedResponse);
		
	}
}
