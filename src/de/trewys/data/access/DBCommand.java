package de.trewys.data.access;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;

public class DBCommand {

	private PreparedStatement ps;
	
	public DBCommand(DBConnection connection, String sql) {
		ps =  createPreparedStatement(connection, sql);
	}

	protected PreparedStatement createPreparedStatement(DBConnection connection, String sql) {
		try {
			return connection.getConnection().prepareStatement(sql, PreparedStatement.NO_GENERATED_KEYS);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void setLong(int index, Long value) {
		try {
			if (value == null)
				ps.setNull(index, Types.INTEGER);
			else
				ps.setLong(index, value);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void setInteger(int index, Integer value) {
		try {
			if (value == null)
				ps.setNull(index, Types.TINYINT);
			else
				ps.setInt(index, value);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void setByte(int index, Byte value) {
		try {
			if (value == null)
				ps.setNull(index, Types.TINYINT);
			else
				ps.setByte(index, value);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void setDouble(int index, Double value) {
		try {
			if (value == null || Double.isNaN(value) || Double.isInfinite(value))
				ps.setNull(index, Types.NUMERIC);
			else
				ps.setDouble(index, value);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void setString(int index, String value) {
		try {
			if (value == null)
				ps.setNull(index, Types.VARCHAR);
			else
				ps.setString(index, value);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void setBytes(int index, byte[] value) {
		try {
			if (value == null)
				ps.setNull(index, Types.BLOB);
			else
				ps.setBytes(index, value);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	

	public void setDate(int index, Date value) {
		try {
			if (value == null)
				ps.setNull(index, Types.TIMESTAMP);
			else
				ps.setTimestamp(index, new Timestamp(value.getTime()));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void setBoolean(int index, Boolean value) {
		try {
			if (value == null)
				ps.setNull(index, Types.BIT);
			else
				ps.setBoolean(index, value);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void setObject(int index, Object value) {
		try {
			if (value == null)
				throw new RuntimeException("NULL not supported");
			else
				ps.setObject(index, value);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	protected PreparedStatement getPreparedStatement() {
		return ps;
	}
	

	public void close() {
		try {
			ps.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
