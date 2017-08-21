package com.heinsd.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.joda.time.DateTime;

/*
 *   Main element of this web service.  An entry represents a location added by a user that has a 
 *   name, description, geographical coordinates, rating, type of location, date and time it was 
 *   created, and the user who created it.  Represents the entry table in database.
 */
@XmlRootElement(name = "Entry")
@XmlAccessorType(XmlAccessType.FIELD)
public class Entry {

	private int id;
	@XmlElement(required = true, name = "point")
	private Point point;
	private String username;
	private PointType type;
	@XmlElement(required = true, name = "name")
	private String name;
	private String description;
	private Rating rating;
	private DateTime datetime;

	public Entry() {
	}

	public Entry(int id, Point point, String username, PointType type, String name, String description, Rating rating,
			DateTime datetime) {
		this.id = id;
		this.point = point;
		this.username = username;
		this.type = type;
		this.name = name;
		this.description = description;
		this.rating = rating;
		this.datetime = datetime;
	}

	public Entry(Point point, String username, PointType type, String name, String description, Rating rating,
			DateTime datetime) {
		this.id = -1;
		this.point = point;
		this.username = username;
		this.type = type;
		this.description = description;
		this.name = name;
		this.rating = rating;
		this.datetime = datetime;
	}

	public int getId() {
		return id;
	}

	public Point getPoint() {
		return point;
	}

	public String getUsername() {
		return username;
	}

	public PointType getType() {
		return type;
	}

	public String getDescription() {
		return description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Rating getRating() {
		return rating;
	}

	public DateTime getDatetime() {
		return datetime;
	}

	@Override
	public String toString() {
		return "Entry [point=" + point + ", username=" + username + ", type=" + type + ", description=" + description
				+ ", rating=" + rating + "]";
	}

}
