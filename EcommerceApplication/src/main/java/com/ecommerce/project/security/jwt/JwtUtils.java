package com.ecommerce.project.security.jwt;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Component
@PropertySource(value = { "classpath:application.properties" })
public class JwtUtils {

	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	@Value("${app.jwt-secret:}")
	private String jwtSecret;

	@Value("${app-jwt-expiration-milliseconds:}")
	private String jwtExpirationDate;

	public String getTokenFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		logger.debug("Authorization header: {}", bearerToken);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}

		return null;
	}

	 public String generateTokenFromUsername(UserDetails userDetails) {
		 String username = userDetails.getUsername();

	        Date currentDate = new Date();

	        Date expireDate = new Date(currentDate.getTime() + Long.parseLong(jwtExpirationDate));	        return Jwts.builder()
	                .subject(username)
	                .issuedAt(new Date())
	                .expiration(expireDate)
	                .signWith(key())
	                .compact();
	    }
	// generate JWT token
	public String generateToken(Authentication authentication) {

		String username = authentication.getName();

		Date currentDate = new Date();

		Date expireDate = new Date(currentDate.getTime() + Long.parseLong(jwtExpirationDate));

		String token = Jwts.builder().subject(username).issuedAt(new Date()).expiration(expireDate).signWith(key())
				.compact();

		return token;
	}

	private Key key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}

	// get username from JWT token
	public String getUsername(String token) {

		return Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(token).getPayload().getSubject();
	}

	// validate JWT token
	public boolean validateToken(String token) {
		try {
			Jwts.parser().verifyWith((SecretKey) key()).build().parse(token);
		}catch(MalformedJwtException e) {
		logger.error("Invalid JWT token: {}", e.getMessage());	
		}catch (ExpiredJwtException e) {
			logger.error("JWT token expired: {}", e.getMessage());	
		}catch (UnsupportedJwtException e) {
			logger.error("JWT token not supported: {}", e.getMessage());	
		}catch (IllegalArgumentException e) {
			logger.error("JWT claim string is empty: {}", e.getMessage());	
		}
		return true;

	}
}
