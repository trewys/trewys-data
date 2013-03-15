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
