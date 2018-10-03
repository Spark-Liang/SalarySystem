package com.lzh.salarysystem;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.lzh.salarysystem.annotation.profile.ProfileValue;
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
@ActiveProfiles(ProfileValue.DEV)
public class SalarySystemApplicationTests {

	
	@Test
	@DatabaseSetup(value = "testdata.xls",
		type = DatabaseOperation.CLEAN_INSERT)
	public void contextLoads() {
	}
	
	
}
