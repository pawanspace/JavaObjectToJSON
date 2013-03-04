package com.dummiesmind.beans;

public class Owner {

	private String name;
	private String email;
	
	public Owner(String name, String email) {
		this.name = name;
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}
	
}
