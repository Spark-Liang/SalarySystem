package com.lzh.salarysystem.ittest.environment;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.dataset.IDataSet;
import org.dbunit.ext.mssql.InsertIdentityOperation;
import org.dbunit.ext.mssql.MsSqlDataTypeFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.lzh.salarysystem.SalarySystemApplication;
import com.lzh.salarysystem.annotation.profile.ProfileValue;
import com.lzh.salarysystem.infrastructure.persistent.repository.EmployeeStreamRepository;
import com.lzh.salarysystem.ittest.common.util.DBUnitEnvironment;
import com.lzh.salarysystem.ittest.common.util.DBUnitEnvironmentBuilder;
import com.lzh.salarysystem.ittest.common.util.XlsDataSetLoader;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SalarySystemApplication.class)
@ActiveProfiles(ProfileValue.ITTEST)
public class SalarySystemITBaseTest implements ApplicationContextAware{

	
	protected ApplicationContext context;
	
	protected static TemporaryFolder tempFileRoot = new TemporaryFolder();
	
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
