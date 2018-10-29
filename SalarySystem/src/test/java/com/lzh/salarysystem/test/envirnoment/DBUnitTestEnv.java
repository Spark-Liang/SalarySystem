package com.lzh.salarysystem.test.envirnoment;

import org.junit.Test;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;

@DBUnitTestEnvironmentPlugin
public class DBUnitTestEnv extends SalarySystemApplicationTests{
	
	private static final String DATA_PATH_PREFIX = "classpath:/";;
	private static final String DATA_PATH = DATA_PATH_PREFIX + "com/lzh/salarysystem/test/envirnoment/";
	
	@Test
	@DatabaseSetup(value = DATA_PATH + "testdata.xls",
		type = DatabaseOperation.CLEAN_INSERT)
	public void loadContext() {}
	
}
