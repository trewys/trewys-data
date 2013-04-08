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
package de.trewys.data.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import de.trewys.data.GenericDataObject;

/**
 * @author og
 *
 */
public class GenericDataObjectHelper {

	private static GenericDataObjectHelper instance;
	
	public static GenericDataObjectHelper getInstance() {
		if (instance == null)
			instance = new GenericDataObjectHelper();
		
		return instance;
	}
	
	private GenericDataObjectHelper() {}
	
	public Collection<GenericDataObject> group(Collection<GenericDataObject> dataObjects, 
			String groupProptery,
			String[] sumProperies) {
		
		Collection<GenericDataObject> groups = new ArrayList<GenericDataObject>();
		
		
		Map<Object, GenericDataObject> groupMap = new HashMap<Object, GenericDataObject>();
		
		for (GenericDataObject genericDataObject : dataObjects) {
			Object key = genericDataObject.getValue(groupProptery);
			GenericDataObject groupDataObject = groupMap.get(key);
			if (groupDataObject == null) {
				groupDataObject = new GenericDataObject();
				groupDataObject.setValue(groupProptery, key);
				groupMap.put(key, groupDataObject);
				for (String sumProperty : sumProperies) {
					groupDataObject.setValue(sumProperty, new Double(0));
				}
				groups.add(groupDataObject);
			}
			
			for (String sumProperty : sumProperies) {
				Double value = genericDataObject.getDouble(sumProperty);
				if (value == null)
					value = 0.0;
				
				groupDataObject.setValue(sumProperty,
						groupDataObject.getDouble(sumProperty) + value);
				
			}
			
		}
		return groups;
	}
	
	public GenericDataObject sum(Collection<GenericDataObject> dataObjects, 
			String[] sumProperies) {
		
		GenericDataObject groupDataObject = new GenericDataObject();
		
		for (GenericDataObject genericDataObject : dataObjects) {
			
			for (String sumProperty : sumProperies) {
				Double value = genericDataObject.getDouble(sumProperty);
				if (value == null)
					value = 0.0;
				
				Double oldValue =  groupDataObject.getDouble(sumProperty);
				
				if (oldValue == null)
					oldValue = 0.0;
				
				groupDataObject.setValue(sumProperty, oldValue + value);
				
			}
			
		}
		return groupDataObject;
	}
	
}
