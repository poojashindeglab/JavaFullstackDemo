package com.jwtproject.springboot_security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jwtproject.springboot_security.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
}