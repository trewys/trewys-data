package de.trewys.data;

import java.util.HashMap;
import java.util.Map;

public class DataFilter {

	private Map<String, Object> filters = new HashMap<String, Object>();
	
	public void setFilter(String name, Object value) {
		filters.put(name, value);
	}
	
	public Object getFilter(String name) {
		return filters.get(name);
	}
}
