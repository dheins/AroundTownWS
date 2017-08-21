package com.heinsd.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.cxf.interceptor.security.AuthenticationException;

import com.heinsd.model.Entry;
import com.heinsd.model.FilterByField;
import com.heinsd.model.Point;
import com.heinsd.model.PointType;
import com.heinsd.model.User;

/*
 * Public interface for CRUD operations in entry and users tables
 * 
 */
public interface DAO {
	int insertEntry(Entry entry);

	boolean deleteEntry(int id, String name);

	List<Entry> getEntries(String username, PointType type, Point point, double dist, FilterByField field)
			throws SQLException;

	User getUser(String username);

	boolean addUser(User user);

	String getUserId(String userneame) throws AuthenticationException;
}
