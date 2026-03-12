package com.ecommerce.project.DTO;

import java.util.List;

public class LoginResponse {

	private String jwtToken;
	private String username;
	List<String> roles;
	public String getJwtToken() {
		return jwtToken;
	}
	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	
	public LoginResponse() {
		super();
	}
	public LoginResponse(String jwtToken, String username, List<String> roles) {
		super();
		this.jwtToken = jwtToken;
		this.username = username;
		this.roles = roles;
	}
	@Override
	public String toString() {
		return "LoginResponse [jwtToken=" + jwtToken + ", username=" + username + ", roles=" + roles + "]";
	}
	
	
}
