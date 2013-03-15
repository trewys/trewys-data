package de.trewys.data.access;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * A wrapper for a database connection. This provides some transaction handling functionality.
 * 
 * @author Ole Golombek
 *
 */
public class DBConnection {

	/**
	 * The inner jdbc connection.
	 */
	private Connection connection;

	public DBConnection(Connection connection) {
		super();
		this.connection = connection;
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	public void startTransaction() {
		try {
			connection.rollback();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void commitTransaction() {
		try {
			connection.commit();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void rollBackTransaction() {
		try {
			connection.rollback();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void close() {
		try {
			rollBackTransaction();
			connection.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public boolean isAutoIdSupported() {
		try {
			return connection.getMetaData().supportsGetGeneratedKeys();
		} catch (SQLException e) {
			return false;
		}
	}
}
