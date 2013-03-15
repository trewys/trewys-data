package de.trewys.data.access;

import java.sql.Connection;

public abstract class DBConnectionHandler {

	/**
	 * The connection string.
	 */
	private String connectionString;
	
	/**
	 * The database user
	 */
	private String dbUser;
	
	/**
	 * The password
	 */
	private String password;
	
	/**
	 * Returns the connection string.
	 * 
	 * @return String : The connection string
	 */
	public String getConnectionString() {
		return connectionString;
	}

	/**
	 * Sets the connection string.
	 * 
	 * @param connectionString
	 * 			String : The connection string
	 */
	public void setConnectionString(String connectionString) {
		this.connectionString = connectionString;
	}

	
	public String getDbUser() {
		return dbUser;
	}

	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public abstract Connection createConnection();
}
