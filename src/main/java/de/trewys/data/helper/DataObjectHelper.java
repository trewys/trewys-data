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

import java.util.Collection;

import de.trewys.data.DataObject;

/**
 * @author og
 *
 */
public class DataObjectHelper {

	private static DataObjectHelper instance;
	
	public static DataObjectHelper getInstance() {
		if (instance == null)
			instance = new DataObjectHelper();
		
		return instance;
	}
	
	private DataObjectHelper() {}
	
	public <E extends DataObject> E getDataObjectById(Collection<E> collection, Long id) {
		
		for (E e : collection) {
			if (e.getId().equals(id))
				return e;
		}

		return null;
	}
	
}
