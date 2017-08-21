package com.heinsd.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "CreateEntryResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class CreateEntryResponse {
	private boolean response;
	private String errMsg;

	public CreateEntryResponse() {}
	public CreateEntryResponse(String err) {
		response = false;
		errMsg = err;
	}
	
	public void setResponse(boolean b) {
		response = b;
	}

	public boolean getData() {
		return response;
	}

	public String getErrMsg() {
		return errMsg;
	}


}
