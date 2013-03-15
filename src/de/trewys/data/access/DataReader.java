package de.trewys.data.access;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import de.trewys.data.DataObject;

/**
 * Implement the <code>read(Resultset rs)</code>-method to create a resultset-dataobject-mapping.
 *  
 * @author Ole Golombek
 *
 * @param <T> DataObject
 */
public abstract class DataReader<T extends DataObject> {

	public abstract T read(ResultSet rs) throws Exception;
	
	/**
	 * Reads an integer value from the resultset with the given name.
	 * Returns <code>null</code> if the filed is empty.
	 *  
	 * @param rs
	 * 			ResultSet : The resultset
	 * @param column
	 * 			String : the name of the column
	 * @return
	 * @throws SQLException
	 */
	protected Integer readInteger(ResultSet rs, String column) throws SQLException {
		Object value = rs.getObject(column);
		if (value == null)
			return null;
		else
			return new Integer(((Number) value).intValue());
	}
	
	protected Byte readByte(ResultSet rs, String column) throws SQLException {
		Object value = rs.getObject(column);
		if (value == null)
			return null;
		else
			return new Byte(((Number) value).byteValue());
	}
	
	protected Long readLong(ResultSet rs, String column) throws SQLException {
		Object value = rs.getObject(column);
		if (value == null)
			return null;
		else
			return new Long(((Number) value).longValue());
	}
	
	protected Double readDouble(ResultSet rs, String column) throws SQLException {
		Object value = rs.getObject(column);
		if (value == null)
			return null;
		else
			return new Double(((Number) value).doubleValue());
	}
	
	protected String readString(ResultSet rs, String column) throws SQLException {
		Object value = rs.getObject(column);
		if (value == null)
			return null;
		else
			return (String) value;
	}
	
	protected Date readDate(ResultSet rs, String column) throws SQLException {
		Object value = rs.getObject(column);
		if (value == null)
			return null;
		else
			return (Date) value;
	}
	
	protected Boolean readBoolean(ResultSet rs, String column) throws SQLException {
		return rs.getBoolean(column);
	}
}
