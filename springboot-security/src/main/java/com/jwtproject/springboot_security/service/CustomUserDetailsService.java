package com.jwtproject.springboot_security.service;
import lombok.AllArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jwtproject.springboot_security.entity.User;
import com.jwtproject.springboot_security.repository.UserRepository;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * CustomUserDetailsService implements UserDetailsService to load user-specific
 * data. It retrieves user information from the UserRepository based on username
 * or email.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not exists by Username or Email"));

        Set<GrantedAuthority> authorities = user.getRoles().stream()
                .map((role) -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(
                usernameOrEmail,
                user.getPassword(),
                authorities
        );
    }
}