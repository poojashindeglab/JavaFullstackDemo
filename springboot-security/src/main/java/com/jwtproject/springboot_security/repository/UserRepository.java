package com.jwtproject.springboot_security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jwtproject.springboot_security.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsernameOrEmail(String username, String email);
	User findByUsername(String username);
	User findByEmail(String email);

}