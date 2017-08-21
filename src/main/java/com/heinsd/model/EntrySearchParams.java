package com.heinsd.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/*
 * Container class for fields in GetEntriesRequest.
 */
@XmlType(name = "EntrySearchParams")
@XmlAccessorType(XmlAccessType.FIELD)
public class EntrySearchParams {
	private String username;
	private PointType type;
	private Point point;
	private double dist;
	@XmlElement(required = true)
	private FilterByField field;

	public EntrySearchParams() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public PointType getType() {
		return type;
	}

	public void setType(PointType type) {
		this.type = type;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public double getDist() {
		return dist;
	}

	public void setDist(double dist) {
		this.dist = dist;
	}

	public FilterByField getField() {
		return field;
	}

	public void setField(FilterByField field) {
		this.field = field;
	}
}