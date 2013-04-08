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
package de.trewys.data.defaultaccess;

import java.lang.reflect.Method;

public class DataObjectProperty {

	private final String column;
	private final Method getter;
	private final Method setter;
	private final Class<?> type;

	DataObjectProperty(String column, Method getter, Method setter, Class<?> type) {
		this.column = column;
		this.getter = getter;
		this.setter = setter;
		this.type = type;
	}

	public String getColumn() {
		return column;
	}

	public Class<?> getType() {
		return type;
	}

	public Object getValue(Object obj) {
		try {
			return getter.invoke(obj, new Object[0]);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void setValue(Object obj, Object value) {
		try {
			setter.invoke(obj, new Object[] {value});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
