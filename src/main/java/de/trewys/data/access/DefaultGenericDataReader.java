/*
 * Copyright 2013 trewys GmbH 
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
 */
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
