package com.heinsd.model;

/*
 *  Main model to represent users.  Represents the users table from the database.
 */
public class User {
	private String id;
	private String username;
	private String email;
	private UserType type;
	private String salt;
	private String pass;
	
	public User(String username, String email, UserType type, String salt, String pass) {
		super();
		this.id = "";
		this.username = username;
		this.email = email;
		this.type = type;
		this.salt = salt;
		this.pass = pass;
	}
	public User(String id, String username, String email, UserType type, String salt, String pass) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.type = type;
		this.salt = salt;
		this.pass = pass;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public UserType getType() {
		return type;
	}
	public void setType(UserType type) {
		this.type = type;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	
	
	
	
}

