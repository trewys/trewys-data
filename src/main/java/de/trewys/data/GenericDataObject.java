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
package de.trewys.data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author og
 *
 */
public class GenericDataObject extends AbstractDataObject {

	private Map<String, Object> values = new HashMap<String, Object>();
	
	public void setValue(String name, Object value) {
		values.put(name, value);
	}
	
	public Object getValue(String name) {
		return values.get(name);
	}
	
	public Integer getInt(String name) {
		Number value = (Number) values.get(name);
		if (value != null)
			return value.intValue();
		
		return null;
	}
	
	public Long getLong(String name) {
		Number value = (Number) values.get(name);
		if (value != null)
			return value.longValue();
		
		return null;
	}
	
	public Double getDouble(String name) {
		Number value = (Number) values.get(name);
		if (value != null)
			return value.doubleValue();
		
		return null;
	}
	
	public Date getDate(String name) {
		return (Date) getValue(name);
	}

	public String getString(String name) {
		return (String) getValue(name);
	}
	
	public Boolean getBoolean(String name) {
		Boolean value = (Boolean) values.get(name);
		if (value != null)
			return value;
		
		return null;
	}
	
	public boolean hasValues() {
		return !values.isEmpty();
	}
}
