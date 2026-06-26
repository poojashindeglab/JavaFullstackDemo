package com.ecommerce.project.security.request;

import java.time.Instant;
import java.util.Objects;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class RefreshToken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String token;
	private String username;
	private Instant expiry;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Instant getExpiry() {
		return expiry;
	}
	public void setExpiry(Instant expiry) {
		this.expiry = expiry;
	}
	public RefreshToken(long id, String token, String username, Instant expiry) {
		super();
		this.id = id;
		this.token = token;
		this.username = username;
		this.expiry = expiry;
	}
	public RefreshToken() {
		super();
	}
	@Override
	public int hashCode() {
		return Objects.hash(expiry, id, token, username);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RefreshToken other = (RefreshToken) obj;
		return Objects.equals(expiry, other.expiry) && id == other.id && Objects.equals(token, other.token)
				&& Objects.equals(username, other.username);
	}
	
	
}
