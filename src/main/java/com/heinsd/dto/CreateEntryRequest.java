package com.heinsd.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.joda.time.DateTime;

import com.heinsd.model.Entry;
import com.heinsd.model.Point;
import com.heinsd.model.PointType;
import com.heinsd.model.Rating;


@XmlType(name = "CreateEntryRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class CreateEntryRequest {
	@XmlElement(required = true)
	private Double latitude;
	@XmlElement(required = true)
	private Double longitude;
	@XmlElement(required = true)
	private String username;
	@XmlElement(required = true)
	private PointType type;
	@XmlElement(required = true)
	private String name;
	@XmlElement(required = true)
	private String description;
	@XmlElement(required = true)
	private Rating rating;
	@XmlElement(required = false)
	private DateTime datetime;
	private int id;

	public Entry getData() {
		Entry entry;
		if (id < 0) {
			entry = new Entry(new Point(longitude, latitude), username, type, name, description, rating, datetime);
		} else {
			entry = new Entry(id, new Point(longitude, latitude), username, type, name, description, rating, datetime);
		}
		return entry;
	}

	public void setDatetime(DateTime datetime) {
		this.datetime = datetime;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}

	public void setType(PointType type) {
		this.type = type;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
