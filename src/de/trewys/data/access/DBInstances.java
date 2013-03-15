package de.trewys.data.access;

import java.util.HashMap;
import java.util.Map;

public class DBInstances {

	public static final String BSMDBI_KEY = "§BSMDBI§";
	
	
	private Map<String, String> keyMap = new HashMap<String, String>();
	
	
	private static DBInstances instance = new DBInstances();

	public static DBInstances getInstance() {
		return instance;
	}
	
	private DBInstances() {
		
	}

	
	public void setKey(String key, String value) {
		this.keyMap.put(key, value);
	}
	
	public String getKeyValue(String key) {
		return this.keyMap.get(key);
	}
	
	public Map<String, String> getKeyMap() {
		return this.keyMap;
	}
}
