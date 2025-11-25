package com.example.fullstack.coupling;

public class UserManager {

	private Provider provider;

	public UserManager(Provider provider) {
		this.provider = provider;
	}

	public String displayUserInfo() {
		return provider.getUserInfo();
	}
}
