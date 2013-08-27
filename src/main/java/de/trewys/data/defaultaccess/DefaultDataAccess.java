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

package de.trewys.data.defaultaccess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import de.trewys.data.DataObject;
import de.trewys.data.PF_History;
import de.trewys.data.access.DBCommand;
import de.trewys.data.access.DBConnection;
import de.trewys.data.access.DataReader;
import de.trewys.data.access.Query;
import de.trewys.data.access.Update;
import de.trewys.data.defaultaccess.DataObjectInfo.SourceType;
import de.trewys.data.historicizer.Historicizer;

public class DefaultDataAccess {

	private static DefaultDataAccess instance = new DefaultDataAccess();

	public static DefaultDataAccess getInstance() {
		return instance;
	}

	private class DefaultDataReader<DO extends DataObject> extends DataReader<DO> {

		private final DataObjectInfo dataObjectInfo;

		public DefaultDataReader(DataObjectInfo info) {
			super();
			this.dataObjectInfo = info;

		}


		@Override
		public DO read(ResultSet rs) throws Exception {
			@SuppressWarnings("unchecked")
			DO data = (DO) dataObjectInfo.getDataClass().getConstructors()[0].newInstance(new Object[0]);

			for (DataObjectProperty property : this.dataObjectInfo.getProperties()) {
				String column = property.getColumn();
				Class<?> colClass = property.getType();
				if (String.class.equals(colClass)) {
					property.setValue(data, readString(rs, column));
				} else if (Long.class.equals(colClass)) {
					property.setValue(data, readLong(rs, column));
				} else if (Integer.class.equals(colClass)) {
					property.setValue(data, readInteger(rs, column));
				} else if (Byte.class.equals(colClass)) {
					property.setValue(data, readByte(rs, column));
				} else if (Double.class.equals(colClass)) {
					property.setValue(data, readDouble(rs, column));
				} else if (byte[].class.equals(colClass)) {
					property.setValue(data, rs.getBytes(column));
				} else if (Date.class.equals(colClass)) {
					property.setValue(data, readDate(rs, column));
				} else if (boolean.class.equals(colClass) || Boolean.class.equals(colClass)) {
					property.setValue(data, readBoolean(rs, column));
				}
			}
			return data;
		}
	};

	private DefaultDataAccess() {

	}

	public <DO extends DataObject> DO getDataObject(DBConnection connection, Class<DO> dataClass, Long id) {
		DataObjectInfo dataObjectInfo = DataObjectConfig.getInstance().getInfo(dataClass);
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT * FROM ");
		if (dataObjectInfo.getSourceType() == SourceType.Query) {
			sql.append("(");
			sql.append(dataObjectInfo.getSource());
			sql.append(")");
		} else
			sql.append(dataObjectInfo.getSource());
		
		sql.append(" D WHERE id = ?");

		Query<DO> query = new Query<DO>(connection, sql.toString());

		try {
			query.setLong(1, id);

			return query.getDataObject(new DefaultDataReader<DO>(dataObjectInfo));
		} finally {
			query.close();
		}
	}

	public <DO extends DataObject> List<DO> getDataObjects(
			DBConnection connection,
			Class<DO> dataClass,
			String where,
			Object... params) {

		DataObjectInfo dataObjectInfo = DataObjectConfig.getInstance().getInfo(dataClass);
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM ");
		if (dataObjectInfo.getSourceType() == SourceType.Query) {
			sql.append("(");
			sql.append(dataObjectInfo.getSource());
			sql.append(")");
		} else
			sql.append(dataObjectInfo.getSource());
		sql.append(" D WHERE ");
		sql.append(where);

		Query<DO> query = new Query<DO>(connection, sql.toString());

		try {
			int i = 1;
			for (Object param : params) {
				setParam(query, i, param);
				i++;
			}

			return query.getDataCollection(new DefaultDataReader<DO>(dataObjectInfo));
		} finally {
			query.close();
		}
	}
	
	public <DO extends DataObject> List<DO> getDataObjectsByQuery(
			DBConnection connection,
			Class<DO> dataClass,
			String sqlQuery,
			Object... params) {

		
		DataObjectInfo dataObjectInfo = DataObjectConfig.getInstance().getInfo(dataClass);
		
		Query<DO> query = new Query<DO>(connection, sqlQuery);

		try {
			int i = 1;
			for (Object param : params) {
				setParam(query, i, param);
				i++;
			}

			return query.getDataCollection(new DefaultDataReader<DO>(dataObjectInfo));
		} finally {
			query.close();
		}
	}

	public <DO extends DataObject> DO getDataObject(
			DBConnection connection,
			Class<DO> dataClass,
			String sqlQuery,
			Object... params) {

		DataObjectInfo dataObjectInfo = DataObjectConfig.getInstance().getInfo(dataClass);
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM ");
		if (dataObjectInfo.getSourceType() == SourceType.Query) {
			sql.append("(");
			sql.append(dataObjectInfo.getSource());
			sql.append(")");
		} else
			sql.append(dataObjectInfo.getSource());
		sql.append(" D WHERE ");
		sql.append(sqlQuery);

		Query<DO> query = new Query<DO>(connection, sql.toString());

		try {
			int i = 1;
			for (Object param : params) {
				setParam(query, i, param);
				i++;
			}

			return query.getDataObject(new DefaultDataReader<DO>(dataObjectInfo));
		} finally {
			query.close();
		}
	}

	public <DO extends DataObject> DO getDataObjectByQuery(
			DBConnection connection,
			Class<DO> dataClass,
			String sqlQuery,
			Object... params) {

		
		DataObjectInfo dataObjectInfo = DataObjectConfig.getInstance().getInfo(dataClass);
		
		Query<DO> query = new Query<DO>(connection, sqlQuery);

		try {
			int i = 1;
			for (Object param : params) {
				setParam(query, i, param);
				i++;
			}

			return query.getDataObject(new DefaultDataReader<DO>(dataObjectInfo));
		} finally {
			query.close();
		}
	}
	
	public void insertDataObject(DBConnection connection, DataObject dataObject) {
		insertDataObject(connection, dataObject, null);
	}
	
	
	public void insertDataObject(DBConnection connection, DataObject dataObject, Historicizer historicizer) {
		Class<?> dataClass = dataObject.getClass();
		DataObjectInfo dataObjectInfo = DataObjectConfig.getInstance().getInfo(dataClass);

		StringBuilder sql = new StringBuilder();

		if (dataObjectInfo.getSourceType() == SourceType.Table) {
			sql.append("INSERT INTO ");
			sql.append(dataObjectInfo.getSource());
			sql.append(" (");
		} else
			throw new RuntimeException("Only table data can be deleted");

		Collection<Object> params = new ArrayList<Object>();

		boolean first = true;
		for (DataObjectProperty property : dataObjectInfo.getProperties()) {
			if (!property.equals(dataObjectInfo.getIdProperty())) {
				if (!first) {
					sql.append(", ");
				}
				
				sql.append(property.getColumn());
				params.add(property.getValue(dataObject));
	
				first = false;
			}
		}

		sql.append(") VALUES (");

		first = true;
		for (int i = 0; i < params.size(); i++) {
			if (!first) {
				sql.append(", ");
			}
			sql.append("?");
			first = false;
		}
		sql.append(")");

		Update update = new Update(connection, sql.toString());

		try {
			int i = 1;
			for (Object param : params) {
				setParam(update, i, param);
				i++;
			}

			update.execute(true);

			dataObjectInfo.getIdProperty().setValue(dataObject, update.getGeneratedId());

		} finally {
			update.close();
		}	
		
		if (historicizer != null) {
			historicizer.historicize(connection);
		}
	}
	
	public void updateDataObject(DBConnection connection, DataObject dataObject) {
		updateDataObject(connection, dataObject, null);
	}

	public void updateDataObject(DBConnection connection, DataObject dataObject, Historicizer historicizer) {
		Class<?> dataClass = dataObject.getClass();
		DataObjectInfo dataObjectInfo = DataObjectConfig.getInstance().getInfo(dataClass);

		StringBuilder sql = new StringBuilder();

		if (dataObjectInfo.getSourceType() == SourceType.Table) {
			sql.append("UPDATE ");
			sql.append(dataObjectInfo.getSource());
			sql.append(" SET ");
		} else
			throw new RuntimeException("Only table data can be deleted");

		boolean first = true;
		List<DataObjectProperty> properties = dataObjectInfo.getProperties();
		for (DataObjectProperty property : properties) {

			if (!property.equals(dataObjectInfo.getIdProperty())) {
				if (!first) {
					sql.append(", ");
				}
				sql.append(property.getColumn());
				sql.append("= ?");
	
				first = false;
			}
		}
		sql.append(" WHERE id = ?");

		Update update = new Update(connection, sql.toString());

		try {
			try {
				int i = 1;
				for (DataObjectProperty property : properties) {
					if (!property.equals(dataObjectInfo.getIdProperty())) {
						Object param = property.getValue(dataObject);
						
						setParam(update, i, param);
						i++;
					}
				}
				update.setLong(i, dataObject.getId());

				update.execute(false);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}


		} finally {
			update.close();
		}		
		
		if (historicizer != null) {
			historicizer.historicize(connection);
		}
	}
	
	public void deleteDataObject(DBConnection connection, DataObject dataObject) {

		Class<?> dataClass = dataObject.getClass();
		DataObjectInfo dataObjectInfo = DataObjectConfig.getInstance().getInfo(dataClass);
		
		StringBuilder sql = new StringBuilder();

		if (dataObjectInfo.getSourceType() == SourceType.Table) {
			sql.append("DELETE FROM ");
			sql.append(dataObjectInfo.getSource());
			sql.append(" WHERE id = ?");
		} else
			throw new RuntimeException("Only table data can be deleted");

		Update update = new Update(connection, sql.toString());

		try {
			update.setLong(1, dataObject.getId());

			update.execute(true);

		} finally {
			update.close();
		}
		
		// clean up history
		if (dataObject.getClass().isAnnotationPresent(History.class)) {
			PF_History history = DefaultDataAccess.getInstance().getDataObject(
					connection, PF_History.class, "entityName = ? AND refID = ?", dataObject.getClass().getSimpleName(), dataObject.getId());
			
			deleteDataObject(connection, history);
		}
	}
	
	public void deleteDataObjects(DBConnection connection, Class<?> dataClass, String sqlQuery, Object... params) {

		DataObjectInfo dataObjectInfo = DataObjectConfig.getInstance().getInfo(dataClass);
		StringBuilder sql = new StringBuilder();
		if (dataObjectInfo.getSourceType() == SourceType.Table) {
			sql.append("DELETE FROM ");
			sql.append(dataObjectInfo.getSource());
			sql.append(" D WHERE ");
			sql.append(sqlQuery);
		} else
			throw new RuntimeException("Only table data can be deleted");

		Update update = new Update(connection, sql.toString());


		try {
			int i = 1;
			for (Object param : params) {
				setParam(update, i, param);
				i++;
			}

			update.execute(false);
		} finally {
			update.close();
		}
		
	}
	
	public void execute(DBConnection connection, String sql, Object... params) {

		Update update = new Update(connection, sql.toString());

		try {
			int i = 1;
			for (Object param : params) {
				setParam(update, i, param);
				i++;
			}

			update.execute(false);

		} finally {
			update.close();
		}
	}

	private void setParam(DBCommand command, int i, Object param) {
		if (param != null) {
			if (param.getClass().equals(Date.class))
				command.setDate(i, (Date) param);
			else
				command.setObject(i, param);
			
		} else {
			command.setByte(i, null);
		}
	}

	public int count(DBConnection connection, String sql, Object... params) {
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {			
			ps = connection.getConnection().prepareStatement(sql, PreparedStatement.NO_GENERATED_KEYS);

			int i = 1;
			for (Object param : params) {
				if (param != null) {
					if (param.getClass().equals(Date.class))
						ps.setTimestamp(i, new Timestamp(((Date) param).getTime()));
					else
						ps.setObject(i, param);
					
				} else {
					ps.setNull(i, Types.BIGINT);
				}
				i++;
			}
			rs = ps.executeQuery();
			
			if (rs.next()) {
				return rs.getInt(1);
			}
			
			return 0;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			try {
				if (ps != null)
					ps.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

}
