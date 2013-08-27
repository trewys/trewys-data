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
