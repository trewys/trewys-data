/**
 * Projekt: trewys-data
 * 
 * $RCSfile: GenericDataObjectHelper.java,v $
 * 
 * @version $Revision: 1.2 $
 * @author  $Author: og $
 *          trewys GmbH
 *          www.trewys.de
 * 
 * $Log: GenericDataObjectHelper.java,v $
 * Revision 1.2  2012-06-22 09:21:50  og
 * *** empty log message ***
 *
 * Revision 1.1  2012-04-23 13:05:15  og
 * *** empty log message ***
 *
 * Revision 1.1  2011-08-30 14:07:06  og
 * *** empty log message ***
 *
 *                                      
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
