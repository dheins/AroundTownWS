package com.heinsd.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/*
 *  Container class to hold all of the fields for the DeleteEntryRequest
 */

@XmlType(name="DeleteParameters")
@XmlAccessorType(XmlAccessType.FIELD)
public class DeleteParameters {
	private int id;
	private String username;
	
	public DeleteParameters() {}
	
	public DeleteParameters(int id, String username) {
		this.id=id;
		this.username = username;
	}
	public int getId() {
		return id;
	}
	public String getUsername() {
		return username;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
