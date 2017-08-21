package com.heinsd.model;

/*
 *  Runtime exception used when Latitude or Longitude is out of range. +-90 and +-180 respectively
 */
public class BadInputException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public BadInputException(String msg) {
		super(msg);
	}

}
