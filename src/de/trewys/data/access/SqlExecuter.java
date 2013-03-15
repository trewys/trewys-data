package de.trewys.data.access;

import java.sql.SQLException;
import java.sql.Statement;

public class SqlExecuter {

	public void execute(DBConnection connection, String sql) {
		
		try {
			Statement stmt = connection.getConnection().createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
}
