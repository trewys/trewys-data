/**
 * Projekt: trewys-data
 * 
 * $RCSfile: DataObjectHelper.java,v $
 * 
 * @version $Revision: 1.1 $
 * @author  $Author: og $
 *          trewys GmbH
 *          www.trewys.de
 * 
 * $Log: DataObjectHelper.java,v $
 * Revision 1.1  2011-08-30 14:07:06  og
 * *** empty log message ***
 *
 *                                      
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
