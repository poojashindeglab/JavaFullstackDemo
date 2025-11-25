package com.jwtproject.springboot_security.service;

import com.jwtproject.springboot_security.dto.LoginDto;

public interface AuthService {
    String login(LoginDto loginDto);
}