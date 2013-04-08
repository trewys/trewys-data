/*
 * Copyright 2013 trewys GmbH 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */
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
