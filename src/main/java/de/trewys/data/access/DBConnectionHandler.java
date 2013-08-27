 /*
 * Copyright 2012 trewys GmbH
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
 * 
 */

package de.trewys.data.access;


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

	public abstract DBConnection createConnection();
}
