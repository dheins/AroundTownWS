package com.heinsd.model;

/*
 * Enum used to define the possible classification of created entries.
 */
public enum PointType {
	FOOD("FOOD"), SERVICE("SERVICE"), AOI("AOI"), TRAFFIC("TRAFFIC"), INFO("INFO");

	private String name;

	PointType(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	public static PointType getType(String s) {
		switch (s) {
		case "FOOD":
			return FOOD;
		case "SERVICE":
			return SERVICE;
		case "AOI":
			return AOI;
		case "TRAFFIC":
			return TRAFFIC;
		case "INFO":
			return INFO;
		default:
			return INFO;
		}
	}
}
