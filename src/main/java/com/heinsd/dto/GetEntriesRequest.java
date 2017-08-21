package com.heinsd.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.heinsd.model.EntrySearchParams;
import com.heinsd.model.FilterByField;
import com.heinsd.model.Point;
import com.heinsd.model.PointType;

@XmlType(name = "GetEntriesRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetEntriesRequest {
	private EntrySearchParams data = new EntrySearchParams();

	public String getUsername() {
		return data.getUsername();
	}

	public void setUsername(String username) {
		this.data.setUsername(username);
	}

	public PointType getType() {
		return data.getType();
	}

	public void setType(PointType type) {
		this.data.setType(type);
	}

	public Point getPoint() {
		return data.getPoint();
	}

	public void setPoint(Point point) {
		this.data.setPoint(point);
	}

	public double getDist() {
		return data.getDist();
	}

	public void setDist(double dist) {
		this.data.setDist(dist);
	}

	public FilterByField getField() {
		return data.getField();
	}

	public void setField(FilterByField field) {
		this.data.setField(field);
	}

	public EntrySearchParams getData() {
		// TODO Auto-generated method stub
		return data;
	}

}
