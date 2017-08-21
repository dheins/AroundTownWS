package com.heinsd.model;

/*
 *  Enum to define rating values.
 */
public enum Rating {
	BAD(-1), OK(0), GOOD(1);
	
	private int rating;
	
	Rating(int num){
		rating =num;
	}
	
	public int getVal() {
		return rating;
	}
	public static Rating getRating(int id) {
		switch(id) {
			case -1:
				return BAD;
			case 0:
				return OK;
			case 1:
				return GOOD;
			default:
				return OK;
		}
	}
	

}
