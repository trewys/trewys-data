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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Update extends DBCommand {

	private Long generatedId;
	
	private String idColumn = null;
	
	public Update(DBConnection connection, String sql) {
		super(connection, sql);
	}

	public Update(DBConnection connection, String sql, String idColumn) {
		super(connection, sql);
		
		this.idColumn = idColumn;
	}

	protected PreparedStatement createPreparedStatement(DBConnection connection, String sql) {
		try {
			return connection.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void execute() {
		execute(true);
	}


	public void execute(boolean getGeneratedId) {
		try {			
	
			PreparedStatement ps = getPreparedStatement();
			
			ps.executeUpdate();
	
			if (getGeneratedId) {
				
				ResultSet rs = ps.getGeneratedKeys();

				String rowId = null;
				try {
					if (rs.next()) {
						if (idColumn == null) {
							generatedId = rs.getLong(1);
						} else {
							//for oracle support...
							String id = idColumn.substring(idColumn.lastIndexOf(".") + 1);
							
							rowId = rs.getString(1);
							StringBuilder sql = new StringBuilder("SELECT ");
							sql.append(id);
							sql.append(" FROM ");
							sql.append(idColumn.substring(0, idColumn.lastIndexOf(".")));
							sql.append(" WHERE ROWID = ?");
							
							PreparedStatement idPS = ps.getConnection().prepareStatement(sql.toString());
							try {
								idPS.setString(1, rowId);
								rs.close();
								rs = idPS.executeQuery();
								
								if (rs.next()) {
									generatedId = rs.getLong(id);
									
							    }
							} finally {
								idPS.close();
							}
						}
				    }
				} finally {
					rs.close();
				}
				
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void addBatch() {
		try {
			getPreparedStatement().addBatch();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void executeBatch() {
		try {
			getPreparedStatement().executeBatch();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Long getGeneratedId() {
		return generatedId;
	}

}
