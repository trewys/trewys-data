package de.trewys.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DataObjectMap<E extends DataObject> {

	public static <T extends DataObject> DataObjectMap<T> createMap(Collection<T> data) {
		DataObjectMap<T> map = new DataObjectMap<T>();
		map.add(data);
		return map;
	}
	
	private Map<Long, E> map = new HashMap<Long, E>();
	
	public void add(Collection<E> data) {
		for (E e : data) {
			add(e);
		}
	}
	
	public void add(E e) {
		map.put(getKey(e), e);
	}
	
	public Collection<E> getDataObjects() {
		return map.values();
	}
	
	public E getDataObject(Long id) {
		return map.get(id);
	}
	
	protected Long getKey(E dataObject) {
		return dataObject.getId();
	}
	
	public void removeDataObject(Long id) {
		map.remove(id);
	}

	public boolean hasDataObject(Long id) {
		return map.containsKey(id);
	}
	
	public int size() {
		return map.keySet().size();
	}
}
