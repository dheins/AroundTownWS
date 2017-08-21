package com.heinsd.model;

/*
 * Enum to define the fields we can filter the GetEntries Request by.
 * If adding a field make sure to update the switch statement in getEntries method
 * in DAOImpl.
 */
public enum FilterByField {
	USER, DIST, TYPE, NONE
}
