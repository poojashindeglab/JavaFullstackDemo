package com.jwtproject.springboot_security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jwtproject.springboot_security.entity.Role;
import com.jwtproject.springboot_security.repository.RoleRepository;

@Service
public class RoleService {

	@Autowired
	RoleRepository roleRepo;
	
	public Role addRole(Role role) {
		return roleRepo.save(role);
	}
}
