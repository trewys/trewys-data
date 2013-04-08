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
