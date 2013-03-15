/**
 * Projekt: trewys-data
 * 
 * $RCSfile: SortedDataObjectMap.java,v $
 * 
 * @version $Revision: 1.1 $
 * @author  $Author: og $
 *          trewys GmbH
 *          www.trewys.de
 * 
 * $Log: SortedDataObjectMap.java,v $
 * Revision 1.1  2012-01-18 09:53:45  og
 * *** empty log message ***
 *
 *                                      
 */
package de.trewys.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author og
 *
 */
public class SortedDataObjectMap<DO extends DataObject> extends DataObjectMap<DO> {

	public static <T extends DataObject> DataObjectMap<T> createMap(Collection<T> data) {
		DataObjectMap<T> map = new SortedDataObjectMap<T>();
		map.add(data);
		return map;
	}
	
	private List<DO> sortedList = new ArrayList<DO>();
	
	@Override
	public void add(DO e) {
		super.add(e);
		sortedList.add(e);
	}

	@Override
	public Collection<DO> getDataObjects() {
		return sortedList;
	}

	@Override
	public void removeDataObject(Long id) {
		DataObject dataObject = super.getDataObject(id);
		if (dataObject != null) {
			super.removeDataObject(id);
			sortedList.remove(dataObject);
		}
	}

	
}
