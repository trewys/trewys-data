package de.trewys.data.access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.microsoft.sqlserver.jdbc.SQLServerConnection;

public class SQLServerDBCHandler extends DBConnectionHandler {

	public SQLServerDBCHandler() {
		super();
		try {
			//load db-driver
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public Connection createConnection() {
		try {
			SQLServerConnection sqlConnection = (SQLServerConnection) DriverManager.getConnection(
					getConnectionString() + "?autoReconnect=true",
			      getDbUser(), getPassword());
			//no auto commiting
			sqlConnection.setAutoCommit(false);

			return sqlConnection;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
