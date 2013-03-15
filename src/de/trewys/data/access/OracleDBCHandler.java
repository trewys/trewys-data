package de.trewys.data.access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import oracle.jdbc.OracleConnection;

public class OracleDBCHandler extends DBConnectionHandler {

	
	public OracleDBCHandler() {
		super();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Connection createConnection() {
		
		try {
			OracleConnection sqlConnection = (OracleConnection) DriverManager.getConnection(
					getConnectionString(),
					getDbUser(), getPassword());
			
			//TODO: Auto Reconnect?

//			sqlConnection.setAutoReconnect(true);
//			sqlConnection.setAutoReconnectForConnectionPools(true);
//			sqlConnection.setAutoReconnectForPools(true);
			
			sqlConnection.setAutoCommit(false);

			return sqlConnection;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
