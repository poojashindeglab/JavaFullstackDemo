package com.ecommerce.project.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.ecommerce.project.Exception.APIException;
import com.ecommerce.project.model.AppRole;
import com.ecommerce.project.model.Role;
import com.ecommerce.project.model.User;
import com.ecommerce.project.repository.RoleRepository;
import com.ecommerce.project.repository.UserRepository;
import com.ecommerce.project.security.jwt.JwtUtils;
import com.ecommerce.project.security.request.LoginRequest;
import com.ecommerce.project.security.request.SignupRequest;
import com.ecommerce.project.security.response.UserInfoResponse;
import com.ecommerce.project.security.service.UserDetailsImpl;

@Service
public class AuthServiceImpl {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	RoleRepository roleRepository;
	
	public UserInfoResponse login(LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		System.out.println("Authentication Object: "+ authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		System.out.println("User Details: " + userDetails);
		//Token based authentication
//		String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);

		ResponseCookie jwtToken = jwtUtils.generateJwtCookie(userDetails);

		System.out.println("JwtToken : "+ jwtToken);
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		UserInfoResponse response = new UserInfoResponse(userDetails.getId(), userDetails.getUsername(), roles,
				jwtToken.toString());
		System.out.print("response : " + response);
		return response;
	}
	
	public User registerUser(SignupRequest signUpRequest) {
	
		 if (userRepository.existsByUserName(signUpRequest.getUsername())) {
	            throw new APIException("Error: Username is already taken!");
	        }

	        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
	            throw new APIException("Error: Email is already in use!");
	        }

	        // Create new user's account
	        User user = new User(signUpRequest.getUsername(),
	                signUpRequest.getEmail(),
	                encoder.encode(signUpRequest.getPassword()));

	        Set<String> strRoles = signUpRequest.getRole();
	        Set<Role> roles = new HashSet<>();

	        if (strRoles == null) {
	            Role userRole = roleRepository.findByRoleName(AppRole.ROLE_USER)
	                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
	            roles.add(userRole);
	        } else {
	            strRoles.forEach(role -> {
	                switch (role) {
	                    case "admin":
	                        Role adminRole = roleRepository.findByRoleName(AppRole.ROLE_ADMIN)
	                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
	                        roles.add(adminRole);

	                        break;
	                    case "seller":
	                        Role modRole = roleRepository.findByRoleName(AppRole.ROLE_SELLER)
	                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
	                        roles.add(modRole);

	                        break;
	                    default:
	                        Role userRole = roleRepository.findByRoleName(AppRole.ROLE_USER)
	                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
	                        roles.add(userRole);
	                }
	            });
	        }

	        user.setRoles(roles);
	        userRepository.save(user);
	        return user;
	}

}
