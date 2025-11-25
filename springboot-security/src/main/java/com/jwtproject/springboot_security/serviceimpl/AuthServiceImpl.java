package com.jwtproject.springboot_security.serviceimpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.jwtproject.springboot_security.config.JwtTokenProvider;
import com.jwtproject.springboot_security.dto.LoginDto;
import com.jwtproject.springboot_security.service.AuthService;

/**
 * This class implements the AuthService interface and provides the login
 * functionality. It uses an AuthenticationManager to authenticate users and a
 * JwtTokenProvider to generate JWT tokens.
 */
@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
		super();
		this.authenticationManager = authenticationManager;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
    public String login(LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(),
                loginDto.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);

        return token;
    }
}