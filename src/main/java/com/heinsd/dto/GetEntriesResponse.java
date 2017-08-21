package com.heinsd.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.heinsd.model.Entry;

@XmlType(name="GetEntriesResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetEntriesResponse {
	private List<Entry> entries;
	private String errMsg;
	
	public GetEntriesResponse() {}
	public GetEntriesResponse(String err) {
		entries = null;
		errMsg = err;
	}
	public String getErrMsg() {
		return errMsg;
	}

	public void setEntriesList(List<Entry> entries) {
		this.entries = entries;
	}

	public List<Entry> getData() {
		return entries;
	}
	
	
}
