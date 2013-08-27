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
import java.util.ArrayList;
import java.util.List;

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
	
	public List<T> getDataCollection(DataReader<T> reader) {		
		ResultSet rs = null;

		try {			
			List<T> collection = new ArrayList<T>();

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
