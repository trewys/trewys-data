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

package de.trewys.data.pool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.trewys.data.access.DBConnection;
import de.trewys.data.access.DBConnectionHandler;

public class MultiConnecionPool {

	/**
	 * Singleton instance.
	 */
	private static MultiConnecionPool instance = new MultiConnecionPool();

	/**
	 * Returns the singleton instance.
	 * 
	 * @return MultiConnecionPool : The instance
	 */
	public static MultiConnecionPool getInstance() {
		return instance;
	}
	
	/**
	 * The list of connections.
	 */
	private Map<String, List<DBConnection>> connections = new HashMap<String, List<DBConnection>>();
	
	/**
	 * The size of the pool.
	 */
	private int poolSize = 20;
	

	private Map<String, DBConnectionHandler> dbConnectionHandlers = new HashMap<String, DBConnectionHandler>();
	
	
	public DBConnectionHandler getDbConnectionHandler(String databaseKey) {
		return dbConnectionHandlers.get(databaseKey);
	}

	public void setDbConnectionHandler(String databaseKey, DBConnectionHandler dbConnectionHandler) {
		this.dbConnectionHandlers.put(databaseKey, dbConnectionHandler);
		this.connections.put(databaseKey, new ArrayList<DBConnection>());
	}

	/**
	 * Constructor.
	 */
	private MultiConnecionPool() {
		
	}
	
	/**
	 * Returns a connection of the pool. If the pool is empty at the moment a new connection
	 * is created.
	 * 
	 * @return DBConnection : A connection
	 */
	public synchronized DBConnection giveConnection(String databaseKey) {
		List<DBConnection> connections = this.connections.get(databaseKey);
		if (connections.size() > 0) {
			//if pool isn't emty => remove last one
			DBConnection connection = connections.remove(connections.size() - 1);
			try {
				connection.rollBackTransaction();
				
				//if connection has been closed (for any reason)
				//this connection will be dropped and a new one will be returned.
				if (connection.getConnection().isClosed())
					return giveConnection(databaseKey);
				
			} catch (SQLException e) {
				return giveConnection(databaseKey);
			}
			
			return connection;
		} else {
			//if no connections are available => create a new one.
			
			DBConnection connection = dbConnectionHandlers.get(databaseKey).createConnection();
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
	public void returnConnection(String databaseKey, DBConnection connection) {
		List<DBConnection> connections = this.connections.get(databaseKey);
		
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
		for (Collection<DBConnection> connections : this.connections.values()) {
			
			for (DBConnection connection : connections) {
				connection.close();
			}
		}
		connections.clear();
	}

}
