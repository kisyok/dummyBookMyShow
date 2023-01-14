package com.dummy.bookmyshow.security;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthRequest {

	@JsonProperty
	private String username;
	@JsonProperty
	private String password;

	public AuthRequest(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public AuthRequest() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "AuthRequest [username=" + username + ", password=" + password + "]";
	}

}
