package com.heinsd.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "DeleteEntryResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class DeleteEntryResponse {
	private boolean response;
	private String errMsg;
	
	public DeleteEntryResponse() {}
	public DeleteEntryResponse(String err) {
		response = false;
		errMsg = err;
	}
	
	public boolean getData() {
		return response;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setResponse(boolean b) {
		response = b;
	}

}
