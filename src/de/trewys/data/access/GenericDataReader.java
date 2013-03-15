package de.trewys.data.access;

import java.sql.ResultSet;

import de.trewys.data.GenericDataObject;


public class GenericDataReader extends DataReader<GenericDataObject> {

	private String[] columns;

	
	public GenericDataReader(String[] columns) {
		this.columns = columns;
	}
	
	@Override
	public GenericDataObject read(ResultSet rs) throws Exception {
		GenericDataObject dataObject = new GenericDataObject();
		
		dataObject.setId(readLong(rs, "id"));
		
		for (String column : columns) {
			dataObject.setValue(column, rs.getObject(column));
		}
		
		return dataObject;
	}

}
