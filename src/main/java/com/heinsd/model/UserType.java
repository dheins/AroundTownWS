package com.heinsd.model;

/*
 * Enum to define types of users for the web service.
 */
public enum UserType {
	GENERAL("GENERAL"), ADMIN("ADMIN");
	
	private String type;
	UserType(String t){
		this.type = t;
	}
	public String getValue() {
		return type;
	}
	
}
