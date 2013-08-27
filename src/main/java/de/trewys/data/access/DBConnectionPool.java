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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Connection pool. With <code>giveConnection()</code> you can receive a connection. If the pool is
 * emtpy, a new connection will be created. 
 * <p>
 * A connection has to be returned to the pool by calling <code>returnConnection(
 * DBConnection connection)</code>.
 * </p>
 * 
 * @author Ole Golombek
 *
 */
public class DBConnectionPool {

	/**
	 * Singleton instance.
	 */
	private static DBConnectionPool instance = new DBConnectionPool();

	/**
	 * Returns the singleton instance.
	 * 
	 * @return DBConnectionPool : The instance
	 */
	public static DBConnectionPool getInstance() {
		return instance;
	}
	
	/**
	 * The list of connections.
	 */
	private List<DBConnection> connections = new ArrayList<DBConnection>();
	
	/**
	 * The size of the pool.
	 */
	private int poolSize = 20;
	

	private DBConnectionHandler dbConnectionHandler;
	
	
	public DBConnectionHandler getDbConnectionHandler() {
		return dbConnectionHandler;
	}

	public void setDbConnectionHandler(DBConnectionHandler dbConnectionHandler) {
		this.dbConnectionHandler = dbConnectionHandler;
	}

	/**
	 * Constructor.
	 */
	private DBConnectionPool() {
		
	}
	
	/**
	 * Returns a connection of the pool. If the pool is empty at the moment a new connection
	 * is created.
	 * 
	 * @return DBConnection : A connection
	 */
	public synchronized DBConnection giveConnection() {
		if (connections.size() > 0) {
			//if pool isn't emty => remove last one
			DBConnection connection = connections.remove(connections.size() - 1);
			try {
				connection.rollBackTransaction();
				
				//if connection has been closed (for any reason)
				//this connection will be dropped and a new one will be returned.
				if (connection.getConnection().isClosed())
					return giveConnection();
				
			} catch (SQLException e) {
				return giveConnection();
			}
			
			return connection;
		} else {
			//if no connections are available => create a new one.
			
			DBConnection connection = dbConnectionHandler.createConnection();
			//connections.add(connection);
			return connection;
			
			
		}
	}
	
	/**
	 * Returns the connection to the pool. All non-committed changes will be condemned.
	 * 
	 * @param connection
	 * 			DBConnection : The connection
	 */
	public void returnConnection(DBConnection connection) {
		if (connections.size() < poolSize) {
			//if there is some room in the pool => add connection
			try {
				connection.rollBackTransaction();
				connections.add(connection);
			} catch (Exception e) {
				try {
					connection.close();
				} catch (Exception ex) {}
			}
		} else {
			//close unneeded connection
			connection.close();
		}
	}
	
	public void closeAllConnections() {
		for (DBConnection connection : connections) {
			connection.close();
		}
		connections.clear();
	}
}
