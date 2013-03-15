package de.trewys.data.defaultaccess;

import java.util.HashMap;
import java.util.Map;

import de.trewys.data.DataObject;

public class DataObjectConfig {

	public static final DataObjectConfig instance = new DataObjectConfig();

	private final Map<Class<?>, DataObjectInfo> dataInfos;

	public static DataObjectConfig getInstance() {
		return instance;
	}

	private DataObjectConfig() {
		this.dataInfos = new HashMap<Class<?>, DataObjectInfo>();
	}

	public <T extends DataObject> DataObjectInfo getInfo(Class<?> clazz) {
		DataObjectInfo dataObjectInfo = this.dataInfos.get(clazz);
		if (dataObjectInfo == null) {
			dataObjectInfo = new DataObjectInfo(clazz);
			this.dataInfos.put(clazz, dataObjectInfo);
		}
		return dataObjectInfo;
	}

}
