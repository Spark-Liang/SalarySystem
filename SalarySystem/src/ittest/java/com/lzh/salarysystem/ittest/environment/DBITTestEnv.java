package com.lzh.salarysystem.ittest.environment;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.dataset.IDataSet;
import org.dbunit.ext.mssql.MsSqlDataTypeFactory;
import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lzh.salarysystem.ittest.common.util.DBUnitEnvironment;
import com.lzh.salarysystem.ittest.common.util.DBUnitEnvironmentBuilder;
import com.lzh.salarysystem.ittest.common.util.XlsDataSetLoader;

public class DBITTestEnv extends SalarySystemITBaseTest{
	
	private static final String DATA_PATH_PREFIX = "classpath:/";
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private DataSource dataSource;
	
	private Map<Class<?>, DBUnitEnvironmentBuilder> builderMap = new ConcurrentHashMap<>();
	
	protected ThreadLocal<DBUnitEnvironment> dbUnitEnvironment = new ThreadLocal<>();
	
	@Before
	public void setUp() throws Exception{
		DBUnitEnvironmentBuilder builder = getDBUnitEnvironmentBuilder();
		dbUnitEnvironment.set(builder.build());
	}
	
	private DBUnitEnvironmentBuilder getDBUnitEnvironmentBuilder() {
		DBUnitEnvironmentBuilder result = builderMap.get(getTestClass());
		if(result != null) {
			return result;
		}
		result = buildDBUnitEnvironmentBuilder();
		return result;
	}

	private DBUnitEnvironmentBuilder buildDBUnitEnvironmentBuilder() {
		DBUnitEnvironmentBuilder result;
		result = new DBUnitEnvironmentBuilder();
		result.setDataSource(getTestedDataSource());
		result.setRootOfBackupFile(tempFileRoot.getRoot());
		result.setTestClass(SalarySystemITBaseTest.class);
		Map<String, Object> configProperties = getConnectionConfig();
		result.getDatabaseProperties().putAll(configProperties);
		return result;
	}

	protected DataSource getTestedDataSource() {
		return dataSource;
	}
	
	protected Class<?> getTestClass() {
		return this.getClass();
	}
	
	protected String getDataPath() {
		return "com/lzh/salarysystem/ittest/environment/";
	}
	
	protected IDataSet loadXlsDataSet(String fileName) {
		String fullDataPath = DATA_PATH_PREFIX + getDataPath() + fileName;
		try {
			return new XlsDataSetLoader()
					.loadDataSet(getTestClass(), fullDataPath);
		} catch (Exception e) {
			logger.info("fail to load dataSet, reason is {}",e);
			return null;
		}
	}
	
	protected Map<String, Object> getConnectionConfig(){
		Map<String, Object> configProperties = new HashMap<>();
		configProperties.put(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new MsSqlDataTypeFactory());
		configProperties.put(DatabaseConfig.FEATURE_BATCHED_STATEMENTS, true);
		configProperties.put(DatabaseConfig.PROPERTY_BATCH_SIZE, 1000);
		return configProperties;
	}
	

	@After
	public void tearDown()throws Exception {
		DBUnitEnvironment environment = dbUnitEnvironment.get();
		if(environment != null) {
			environment.destory();
		}
	}
}
