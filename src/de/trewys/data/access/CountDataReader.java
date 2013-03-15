package de.trewys.data.access;

import java.sql.ResultSet;


public class CountDataReader extends DataReader<Count> {

	private static CountDataReader instance = new CountDataReader();
	
	public static CountDataReader getInstance() {
		return instance;
	}
	
	private CountDataReader() {}
	
	@Override
	public Count read(ResultSet rs) throws Exception {
		return new Count(rs.getInt(1));
	}

}
