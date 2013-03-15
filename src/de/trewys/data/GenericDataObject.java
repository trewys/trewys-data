/**
 * Projekt: trewys-data
 * 
 * $RCSfile: GenericDataObject.java,v $
 * 
 * @version $Revision: 1.6 $
 * @author  $Author: sg $
 *          trewys GmbH
 *          www.trewys.de
 * 
 * $Log: GenericDataObject.java,v $
 * Revision 1.6  2012-06-20 16:14:44  sg
 * - Port Schedule field: preliminary
 *
 * Revision 1.5  2012-04-23 13:05:15  og
 * *** empty log message ***
 *
 * Revision 1.4  2011-11-07 08:16:11  og
 * *** empty log message ***
 *
 * Revision 1.3  2011-09-15 09:36:48  vg
 * nächste Spiele
 *
 * Revision 1.2  2011-09-05 07:19:22  og
 * *** empty log message ***
 *
 * Revision 1.1  2011-08-16 11:07:06  og
 * *** empty log message ***
 *
 *                                      
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
