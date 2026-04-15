package com.ecommerce.project.security.request;
import java.util.Set;

import com.ecommerce.project.annotation.ValidPassword;

import jakarta.validation.constraints.*;


public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String user_name;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    private Set<String> role;

    @NotBlank
    @Size(min = 6, max = 40)
    @ValidPassword
    private String password;

     
    public SignupRequest() {
		super();
	}

	public SignupRequest(@NotBlank @Size(min = 3, max = 20) String username,
			@NotBlank @Size(max = 50) @Email String email, Set<String> role,
			@NotBlank @Size(min = 6, max = 40) String password) {
		super();
		this.user_name = username;
		this.email = email;
		this.role = role;
		this.password = password;
	}

	public String getUsername() {
		return user_name;
	}

	public void setUsername(String username) {
		this.user_name = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<String> getRole() {
        return this.role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }
}