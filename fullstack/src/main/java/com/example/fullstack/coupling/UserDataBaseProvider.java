package com.example.fullstack.coupling;

public class UserDataBaseProvider implements Provider {

	@Override
	public String getUserInfo() {
		return "Fetching user info from User Database";
	}

}
