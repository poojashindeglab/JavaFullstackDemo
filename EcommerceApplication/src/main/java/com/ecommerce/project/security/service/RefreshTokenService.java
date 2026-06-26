package com.ecommerce.project.security.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.project.repository.RefreshTokenRepository;
import com.ecommerce.project.security.request.RefreshToken;

@Service
public class RefreshTokenService {

	@Autowired
	private RefreshTokenRepository refRepository;
	
	public RefreshToken createToken(String username) {
		RefreshToken refToken = new RefreshToken();
		refToken.setUsername(username);
		refToken.setToken(UUID.randomUUID().toString());
		refToken.setExpiry(Instant.now().plusMillis(608400000));
		return refRepository.save(refToken);
	}
	
	
	public RefreshToken verifyRefreshToken(String token) {
		RefreshToken refToken = refRepository.findByToken(token).orElseThrow(() -> new RuntimeException("Invalid Refresh Token"));
		
		if(refToken.getExpiry().compareTo(Instant.now()) < 0) {
			refRepository.delete(refToken);
			throw new RuntimeException("RefreshToken expired.");
		}
		return refToken;
	}
}
