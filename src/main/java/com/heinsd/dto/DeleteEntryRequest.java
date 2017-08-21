package com.heinsd.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.heinsd.model.DeleteParameters;

@XmlType(name = "DeleteEntryRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class DeleteEntryRequest {
	private int id;
	private String username;

	public DeleteParameters getData() {
		return new DeleteParameters(id, username);
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
