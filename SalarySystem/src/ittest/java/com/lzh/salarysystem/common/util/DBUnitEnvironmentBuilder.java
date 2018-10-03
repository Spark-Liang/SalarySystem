package com.lzh.salarysystem.common.util;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.springframework.jdbc.datasource.DataSourceUtils;

public class DBUnitEnvironmentBuilder {
	
	private Class<?> testClass;
	
	private File rootOfBackupFile;
	
	private DataSource dataSource;
	
	private Map<String, Object> databaseProperties = new HashMap<>();
	
	public DBUnitEnvironment build() throws Exception{
		return build(null);
	}
	
	public DBUnitEnvironment build(Class<?> testClass) throws Exception{
		return build(testClass != null ? testClass : this.testClass, null);
	}
	
	public DBUnitEnvironment build(Class<?> testClass ,Map<String, Object> properties) throws Exception{
		Map<String, Object> configProperties = new HashMap<>();
		if(properties != null) {
			configProperties.putAll(properties);
		}
		return build(null, testClass, configProperties);
	}
	
	public DBUnitEnvironment build(Connection connection ,Class<?> testClass,Map<String, Object> properties) throws Exception{
		IDatabaseConnection conn;
		if(connection == null) {
			conn = new DatabaseConnection(DataSourceUtils.getConnection(dataSource));
		}else {
			conn = new DatabaseConnection(connection);
		}
		
		databaseProperties.putAll(properties);
		DatabaseConfig config = conn.getConfig();
		for(Entry<String, Object> entry : databaseProperties.entrySet()) {
			config.setProperty(entry.getKey(), entry.getValue());
		}
		
		return new DBUnitEnvironment(conn,rootOfBackupFile,testClass);
	}

	public Class<?> getTestClass() {
		return testClass;
	}

	public void setTestClass(Class<?> testClass) {
		this.testClass = testClass;
	}

	public File getRootOfBackupFile() {
		return rootOfBackupFile;
	}

	public void setRootOfBackupFile(File rootOfBackupFile) {
		this.rootOfBackupFile = rootOfBackupFile;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Map<String, Object> getDatabaseProperties() {
		return databaseProperties;
	}

	public void setDatabaseProperties(Map<String, Object> databaseProperties) {
		this.databaseProperties = databaseProperties;
	}

	
	
}
