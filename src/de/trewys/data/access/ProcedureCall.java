package de.trewys.data.access;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProcedureCall extends DBCommand {

	public ProcedureCall(DBConnection connection, String sql) {
		super(connection, sql);
	}
	
	@Override
	protected PreparedStatement createPreparedStatement(
			DBConnection connection, String sql) {
		try {
			return connection.getConnection().prepareCall(sql);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}
	
	public void addOutputParameter(int i, Class<?> returnClass) {

		try {
			CallableStatement cs = (CallableStatement) getPreparedStatement();
			if (returnClass.equals(Long.class))
				cs.registerOutParameter(i, java.sql.Types.BIGINT);
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	public Long getLong(int i) {
		try {
			return ((CallableStatement) getPreparedStatement()).getLong(i);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void execute() {
		try {			

			PreparedStatement ps = getPreparedStatement();
			ps.execute();
		
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	

}
