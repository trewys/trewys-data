package de.trewys.data.access;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import de.trewys.data.DataObject;


public class Query<T extends DataObject> extends DBCommand {

	
	public Query(DBConnection connection, String sql) {
		super(connection, sql);
	}

	public T getDataObject(DataReader<T> reader) {
		ResultSet rs = null;

		try {			

			PreparedStatement ps = getPreparedStatement();

			rs = ps.executeQuery();
			
			if (rs.next()) {
				return reader.read(rs);
			}
			
			return null;
		} catch (Exception e) {

			throw new RuntimeException(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	public Collection<T> getDataCollection(DataReader<T> reader) {		
		ResultSet rs = null;

		try {			
			Collection<T> collection = new ArrayList<T>();

			PreparedStatement ps = getPreparedStatement();
			rs = ps.executeQuery();
			
			while (rs.next()) {
				collection.add(reader.read(rs));
			}
			
			return collection;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
}
