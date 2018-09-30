package com.lzh.salarysystem;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.ext.mssql.MsSqlDataTypeFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.lzh.salarysystem.annotation.profile.ProfileValue;
import com.lzh.salarysystem.common.util.DBUnitEnvironment;
import com.lzh.salarysystem.common.util.DBUnitEnvironmentBuilder;
import com.lzh.salarysystem.ittest.ClassNameBasedXlsDataSetLoader;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SalarySystemApplication.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    TransactionDbUnitTestExecutionListener.class,
    DbUnitTestExecutionListener.class})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@DbUnitConfiguration(
		databaseConnection = {"druidDataSource"}
		,dataSetLoader = ClassNameBasedXlsDataSetLoader.class)
@ActiveProfiles(ProfileValue.ITTEST)
public class SalarySystemITBaseTest implements ApplicationContextAware{

	
	protected ApplicationContext context;
	
	@Autowired
	private DataSource dataSource;
	
	
	protected static TemporaryFolder tempFileRoot = new TemporaryFolder();
	
	private Map<Class<?>, DBUnitEnvironmentBuilder> builderMap = new ConcurrentHashMap<>();
	
	protected ThreadLocal<DBUnitEnvironment> dbUnitEnvironment = new ThreadLocal<>();
	
	@BeforeClass
	public static void setUpEnvironment() throws IOException {
		tempFileRoot.create();
	}
	
	@AfterClass
	public static void teardownEnvironment() {
		if(tempFileRoot != null) {
			tempFileRoot.delete();
		}
	}
	
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
	
	@AfterClass
	public static void tearAllDown() {
		if(tempFileRoot != null) {
			tempFileRoot.delete();
		}
	}
	
	//@Test
	public void loadContext() {
		
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}
}
