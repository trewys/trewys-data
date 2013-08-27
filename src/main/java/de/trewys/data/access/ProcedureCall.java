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

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProcedureCall extends DBCommand {

	public ProcedureCall(DBConnection connection, String sql) {
		super(connection, sql);
	}
	
	@Override
	protected PreparedStatement createPreparedStatement(
			DBConnection connection, String sql) {
		try {
			return connection.getConnection().prepareCall(sql);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}
	
	public void addOutputParameter(int i, Class<?> returnClass) {

		try {
			CallableStatement cs = (CallableStatement) getPreparedStatement();
			if (returnClass.equals(Long.class))
				cs.registerOutParameter(i, java.sql.Types.BIGINT);
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	public Long getLong(int i) {
		try {
			return ((CallableStatement) getPreparedStatement()).getLong(i);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void execute() {
		try {			

			PreparedStatement ps = getPreparedStatement();
			ps.execute();
		
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	

}
