package de.trewys.data.defaultaccess;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import de.trewys.data.access.SQLStatementReader;

public class DataObjectInfo {

	private final Class<?> dataClass;
	private final String source;
	private DataObjectProperty idProperty;
	private final List<DataObjectProperty> properties;
	

	protected DataObjectInfo(Class<?> dataClass) {
		this.dataClass = dataClass;

		if (dataClass.isAnnotationPresent(DataObjectSource.class)) {
			String sourceFile = dataClass.getAnnotation(DataObjectSource.class).file();
			String sourceTable = dataClass.getAnnotation(DataObjectSource.class).table();
			if (sourceFile != null && !"".equals(sourceFile)) {
				this.source = "(" + SQLStatementReader.getInstance().getSQLFile(sourceFile) + ")";
			} else if (sourceTable != null && !"".equals(sourceTable)) {
				this.source = sourceTable;
			} else {
				throw new RuntimeException("Illegal source attribute: " + this.source); //TODO: Table / View
			}
		} else {
			source = dataClass.getSimpleName();
		}
		
		this.properties = new ArrayList<DataObjectProperty>();

		for (Method method : dataClass.getMethods()) {
			if (method.getName().startsWith("set")) {
				final String localFieldName = method.getName().substring(3);
				String column;
				if (method.isAnnotationPresent(DataObjectColumn.class)) {
					column = method.getAnnotation(DataObjectColumn.class).value();
				} else {
					column = localFieldName.substring(0, 1).toLowerCase().concat(localFieldName.substring(1));
				}
				final Class<?> colClass = method.getParameterTypes()[0];
				final Method setter = method;
				Method getter = null;
				try {
					if (colClass == boolean.class) {
						getter = dataClass.getMethod("is".concat(localFieldName));
					}
					else {
						getter = dataClass.getMethod("get".concat(localFieldName));
					}
				} catch (Exception e) {
					throw new RuntimeException(e);
				}

				DataObjectProperty property = new DataObjectProperty(column, getter, setter, colClass);
				if (this.idProperty == null && "id".equals(column)) {
					this.idProperty = property;
				}
				this.properties.add(property);
			}
		}
	}

	public Class<?> getDataClass() {
		return dataClass;
	}

	public List<DataObjectProperty> getProperties() {
		return properties;
	}

	public DataObjectProperty getIdProperty() {
		return this.idProperty;
	}

	public String getSource() {
		return source;
	}

}
