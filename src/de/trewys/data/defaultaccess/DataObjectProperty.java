package de.trewys.data.defaultaccess;

import java.lang.reflect.Method;

public class DataObjectProperty {

	private final String column;
	private final Method getter;
	private final Method setter;
	private final Class<?> type;

	DataObjectProperty(String column, Method getter, Method setter, Class<?> type) {
		this.column = column;
		this.getter = getter;
		this.setter = setter;
		this.type = type;
	}

	public String getColumn() {
		return column;
	}

	public Class<?> getType() {
		return type;
	}

	public Object getValue(Object obj) {
		try {
			return getter.invoke(obj, new Object[0]);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void setValue(Object obj, Object value) {
		try {
			setter.invoke(obj, new Object[] {value});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
