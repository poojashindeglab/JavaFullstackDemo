package com.example.fullstack.coupling;

public class WebServiceProvider implements Provider {

	@Override
	public String getUserInfo() {
		return "Fetching user info from Web Service";
	}

}
