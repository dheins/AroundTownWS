package com.heinsd.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.cxf.interceptor.security.AuthenticationException;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import com.heinsd.model.BadInputException;
import com.heinsd.model.Entry;
import com.heinsd.model.FilterByField;
import com.heinsd.model.Point;
import com.heinsd.model.PointType;
import com.heinsd.model.Rating;
import com.heinsd.model.User;
import com.heinsd.model.UserType;

public class DAOImpl implements DAO {
	@Resource(name = "jdbc/pointsDB")
	private static DataSource dataSource;
	private final String insert = "INSERT INTO entry VALUES (null, ?, ?, ?, ?,?,?,?,?)";
	private final String delete = "DELETE FROM entry WHERE id = ? AND username = ?";
	private final static Logger log = Logger.getLogger(DAOImpl.class);

	private DAOImpl() {
	}

	// Get instance of DAOImpl and create the DataSource from
	// META-INF/context.xml
	public static DAOImpl getInstance() {
		Context initContext;
		DAOImpl entryDAOImpl = new DAOImpl();
		if (dataSource == null) {
			try {
				initContext = new InitialContext();
				Context envContext = (Context) initContext.lookup("java:/comp/env");
				dataSource = (DataSource) envContext.lookup("jdbc/pointsDB");

			} catch (NamingException e) {
				log.error("Unable to set DataSource - " + e.getMessage());
				dataSource = null;
			}
		}
		return entryDAOImpl;
	}

	// Insert new entry into entry table
	@Override
	public int insertEntry(Entry entry) {
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = dataSource.getConnection();

			stmt = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			stmt.setDouble(1, entry.getPoint().getLongitude());
			stmt.setDouble(2, entry.getPoint().getLatitude());
			stmt.setString(3, entry.getType().toString());
			stmt.setString(4, entry.getName());
			stmt.setString(5, entry.getDescription());
			stmt.setString(6, entry.getUsername());
			stmt.setInt(7, entry.getRating().getVal());
			Timestamp ts = new Timestamp(DateTime.now().getMillis());
			stmt.setTimestamp(8, ts);

			stmt.executeUpdate();
			// Get the auto-generated id for the newly inserted entry
			int id = -1;
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				id = rs.getInt(1);
			}
			stmt.close();
			return id;
		} catch (SQLException e) {
			logSqlException("Sql error inserting entry", e);

		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					logSqlException("Sql error closing connection", e);
				}
			}
		}

		return -1;
	}

	// Delete entry from entry table
	@Override
	public boolean deleteEntry(int id, String name) {
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = dataSource.getConnection();
			stmt = connection.prepareStatement(delete);
			stmt.setInt(1, id);
			stmt.setString(2, name);
			int success = stmt.executeUpdate();
			stmt.close();
			if (success > 0) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			logSqlException("Sql error deleting entry", e);
			;
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					logSqlException("Sql error closing connection", e);
				}
			}
		}
		return false;
	}

	// Get entries from entry table. Results can be filtered by elements in
	// FilterByField enum
	@Override
	public List<Entry> getEntries(String username, PointType type, Point point, double dist, FilterByField field)
			throws SQLException {
		Connection connection = null;
		PreparedStatement stmt = null;
		CallableStatement callstmt = null;
		List<Entry> list = new ArrayList<>();
		try {
			connection = dataSource.getConnection();
			ResultSet results = null;
			if (field == null)
				field = FilterByField.NONE;
			switch (field) {
			// Filter by distance from point
			case DIST:
				callstmt = connection.prepareCall("{call pointsWithinRadius(?,?,?}");
				callstmt.setDouble(1, point.getLongitude());
				callstmt.setDouble(2, point.getLongitude());
				callstmt.setDouble(3, dist);
				results = callstmt.executeQuery();

				break;
			// Filter by entry type
			case TYPE:
				stmt = connection.prepareStatement("SELECT * FROM entry WHERE entryType = ?");
				stmt.setString(1, type.toString());
				results = stmt.executeQuery();
				break;
			// Filter by user that created the entry
			case USER:
				stmt = connection.prepareStatement("SELECT * FROM entry WHERE username = ?");
				stmt.setString(1, username);
				results = stmt.executeQuery();
				break;
			// No filter
			case NONE:
				stmt = connection.prepareStatement("SELECT * FROM entry");
				results = stmt.executeQuery();
			}

			// Iterate results and build list of entry
			while (results.next()) {
				int id = results.getInt(1);
				double lon = results.getDouble(2);
				double lat = results.getDouble(3);
				Point p = null;
				try {
					p = new Point(lon, lat);
				} catch (BadInputException e) {
					log.error("Bad input exception - Lat/Long not in correct format- Entry id: " + id);

				}
				PointType ty = PointType.getType(results.getString(4));
				String name = results.getString(5);
				String desc = results.getString(6);
				String user = results.getString(7);
				int rate = results.getInt(8);
				Rating rating = Rating.getRating(rate);
				Timestamp ts = results.getTimestamp(9);
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
				DateTime dt = new DateTime(df.format(ts));

				Entry e = new Entry(id, p, user, ty, name, desc, rating, dt);
				list.add(e);
			}
			if (results != null)
				results.close();
			if (stmt != null)
				stmt.close();
			if (callstmt != null)
				callstmt.close();

			return list;

		} catch (SQLException e) {
			logSqlException("Sql error getting entries", e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					logSqlException("Sql error closing connection", e);
				}
			}
		}
		return null;

	}
	// Get user from users table
	@Override
	public User getUser(String username) {

		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = dataSource.getConnection();
			stmt = connection.prepareStatement("Select * from users where username = ?");
			stmt.setString(1, username);

			ResultSet result = stmt.executeQuery();
			while (result.next()) {
				User user = new User(result.getString(1), username, result.getString(3),
						UserType.valueOf(result.getString(4)), result.getString(5), result.getString(6));
				result.close();
				stmt.close();
				return user;
			}

		} catch (SQLException e) {
			logSqlException("Sql error getting user", e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					logSqlException("Sql error closing connection", e);

				}
			}
		}
		return null;
	}
	
	// Insert new user into users table
	@Override
	public boolean addUser(User user) {

		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = dataSource.getConnection();
			stmt = connection.prepareStatement("INSERT INTO users VALUE(null , ?, ?, ?, ?, ?, ? )");
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getEmail());
			stmt.setString(3, user.getType().getValue());
			stmt.setString(4, user.getSalt());
			stmt.setString(5, user.getPass());

			int result = stmt.executeUpdate();

			stmt.close();
			if (result == 1)
				return true;
			return false;
		} catch (SQLException e) {
			logSqlException("Sql error creating user", e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					logSqlException("Sql error closing connection", e);
				}
			}
		}
		return false;
	}
	
	// Get users id from users table
	@Override
	public String getUserId(String username) throws AuthenticationException {
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = dataSource.getConnection();
			stmt = connection.prepareStatement("Select id from users where username = ?");
			stmt.setString(1, username);
			ResultSet result = stmt.executeQuery();
			while (result.next()) {
				String id = result.getString(1);
				result.close();
				stmt.close();

				return id;
			}
			throw new AuthenticationException("Username not found");

		} catch (SQLException e) {
			logSqlException("Sql error getting user id", e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					logSqlException("Sql error closing connection", e);
				}
			}
		}
		return null;
	}

	private void logSqlException(String msg, SQLException e) {
		log.error(msg + " - Sql State: " + e.getSQLState() + " - Error code: " + e.getErrorCode() + " - Message: "
				+ e.getMessage());
	}

}
