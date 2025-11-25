package com.jwtproject.springboot_security.service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.jwtproject.springboot_security.entity.Role;
import com.jwtproject.springboot_security.entity.User;
import com.jwtproject.springboot_security.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserDetailService {

	@Mock
	private UserRepository userRepository;
	
	@InjectMocks
	private CustomUserDetailsService customUserDetailsService;
	
	
	@Test
	void testLoadUserByUsername() {
		Role role = new Role();
		role.setId(1L);
		role.setName("ROLE_ADMIN");
		
		Set<Role> roleSet = Set.of(role);
		User user = new User();
		user.setId(1L);
		user.setName("testuser");
		user.setUsername("testuser123");
		user.setEmail("testuser@gmail.com");
		user.setPassword("test123");
	
		user.setRoles(roleSet);
		Optional<User> optUser = Optional.of(user);


		Mockito.when(userRepository.findByUsernameOrEmail("testuser123", "testuser123")).thenReturn(optUser);
		UserDetails userDetails = customUserDetailsService.loadUserByUsername("testuser123");
		assert(userDetails.getUsername().equals(user.getUsername()));
	}
}
