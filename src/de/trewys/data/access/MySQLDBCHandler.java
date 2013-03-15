package de.trewys.data.access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.ConnectionImpl;

public class MySQLDBCHandler extends DBConnectionHandler {

	
	public MySQLDBCHandler() {
		super();
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Connection createConnection() {
		
		try {
			ConnectionImpl sqlConnection = (ConnectionImpl) DriverManager.getConnection(
					getConnectionString() + "?autoReconnect=true", getDbUser(), getPassword());

			sqlConnection.setAutoReconnect(true);
			sqlConnection.setAutoReconnectForConnectionPools(true);
			sqlConnection.setAutoReconnectForPools(true);
			
			sqlConnection.setAutoCommit(false);

			return sqlConnection;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
