package com.heinsd.test.dao;

import java.sql.SQLException;
import java.util.List;

import org.joda.time.DateTime;

import com.heinsd.dao.EntryDAO;
import com.heinsd.dao.EntryDAOImpl;
import com.heinsd.model.Entry;
import com.heinsd.model.FilterByField;
import com.heinsd.model.Point;
import com.heinsd.model.PointType;
import com.heinsd.model.Rating;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import junit.framework.TestCase;

public class EntryDAOTest extends TestCase {
	int idNum = 19;
	private EntryDAO entryDao;
	
	public void setUp() {

		String url = "jdbc:mysql://localhost:3306/points";
		String user="heinsd";
		String pass="BrySqlMad79!";
		
		MysqlDataSource ds = new MysqlDataSource();
		ds.setURL(url);
		ds.setUser(user);
		ds.setPassword(pass);
		entryDao = new EntryDAOImpl();
		((EntryDAOImpl)entryDao).setDataSource(ds);
		
	}

	public void testInsertEntry() {
		setUp();
		Entry e = new Entry(new Point(1,1), "me", PointType.AOI, "test", Rating.BAD, DateTime.now());
		try {
			int id = entryDao.insertEntry(e);
			assertEquals(idNum,id);
		}catch(Exception exception) {
			fail(e.getDescription());
		}
		
		
		
	}

	public void testDeleteEntry() {
		Entry e = new Entry(idNum, new Point(1,1), "me", PointType.AOI, "test", Rating.BAD, DateTime.now());

		boolean deleteEntry = entryDao.deleteEntry(e);
		if(!deleteEntry) {
			fail("Delete failed");
		}
		deleteEntry = entryDao.deleteEntry(e);
		if(deleteEntry) {
			fail("Deleted non existing object");
		}
		
	}

	public void testGetEntries() {
		List<Entry> entries;
		try {
			entries = entryDao.getEntries("me", null, null, 0, FilterByField.NONE);
			if(entries == null) {
				fail("Results Null");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
