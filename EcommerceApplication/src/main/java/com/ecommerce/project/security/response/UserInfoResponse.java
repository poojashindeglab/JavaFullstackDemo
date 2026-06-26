package com.ecommerce.project.security.response;

import java.util.List;

public class UserInfoResponse {
    private Integer id;
    private String jwtToken;
    private String refToken;


	public String getRefToken() {
		return refToken;
	}

	public void setRefToken(String refToken) {
		this.refToken = refToken;
	}

	private String username;
    private List<String> roles;

    public UserInfoResponse() {
		super();
	}

    public UserInfoResponse(Integer id, String username, String jwtToken, List<String> roles) {
		super();
		this.id = id;
		this.jwtToken = jwtToken;
		this.username = username;
		this.roles = roles;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
}


