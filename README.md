# trewys-data [![Build Status](https://travis-ci.org/trewys/trewys-data.png?branch=master)](https://travis-ci.org/trewys/trewys-data)

Trewys data access framework


### Implement your MySQL DataAccessHandler
---

We provide interfaces for MySQL, Oracle and SQLServer

* de.trewys.data.access.AbstractMySQLDBCHandler
* de.trewys.data.access.AbstractOracleDBCHandler
* de.trewys.data.access.AbstractSQLServerDBCHandler

You just need to extend your own Implementation.

```java
// [...]
public class MySQLDBCHandler extends AbstractMySQLDBCHandler{

	@Override
	public Connection createConnection() {
		try {
			com.mysql.jdbc.ConnectionImpl sqlConnection = (com.mysql.jdbc.ConnectionImpl) DriverManager.getConnection(
					getConnectionString() + "?autoReconnect=true", getDbUser(), getPassword());

			sqlConnection.setAutoReconnect(true);
			sqlConnection.setAutoReconnectForConnectionPools(true);
			sqlConnection.setAutoReconnectForPools(true);
			
			sqlConnection.setAutoCommit(false);

			return sqlConnection;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	// [...]
}

// [...]
```

Analogous for SQLServer and Oracle.


If you need to implement other DataBaseConnectionHandlers we strongly recomment to keep thy type hierarchy. 
Create an abstract class, which prooves whether the driver class is enabled on your classpath or not.