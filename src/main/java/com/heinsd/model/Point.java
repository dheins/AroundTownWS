package com.heinsd.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/*
 *  Represents a geographical location by a lat long pair.  If the latitude or longitude is 
 *  out of range it throws a BadInputException.
 */
@XmlType(name="Point")
@XmlAccessorType(XmlAccessType.FIELD)
public class Point {
	private double latitude;
	private double longitude;

	public Point(double longitude,double latitude) throws BadInputException {
		// Check latitude is between +- 90
		if (latitude < -90 || latitude > 90) {
			throw new BadInputException("Latitude must be between -90 and 90.  Actual=" + latitude);
		}
		// Check longitude is between +- 180
		if (longitude < -180 || longitude > 180) {
			throw new BadInputException("Longitude must be between -180 and 180.  Actual=" + longitude);
		}

		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
	public double getLatitude() {
		return latitude;
	}

	@Override
	public String toString() {
		return "(" + latitude + ", " + longitude + ")";
	}
	

}
