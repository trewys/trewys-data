package de.trewys.data.access;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import de.trewys.data.GenericDataObject;


public class DefaultGenericDataReader extends DataReader<GenericDataObject> {

	private static DefaultGenericDataReader instance = new DefaultGenericDataReader();

	public static DefaultGenericDataReader getInstance() {
		return instance;
	}
	
	private DefaultGenericDataReader() {
		
	}
	
	@Override
	public GenericDataObject read(ResultSet rs) throws Exception {
		GenericDataObject dataObject = new GenericDataObject();
		
		ResultSetMetaData metaData = rs.getMetaData();
		for (int i = 0; i < metaData.getColumnCount() ; i++) {
			String column = metaData.getColumnName(i + 1);
			dataObject.setValue(column, rs.getObject(column));
		}
		
		return dataObject;
	}

}
