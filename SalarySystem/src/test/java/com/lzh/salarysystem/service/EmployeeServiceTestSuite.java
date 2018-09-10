package com.lzh.salarysystem.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({
	
	EmployeeServiceTest_addEmployeeTest_testAddMonthlyEmployee.class,
	EmployeeServiceTest_addEmployeeTest_testAddSaleEmployee.class,
	EmployeeServiceTest_addEmployeeTest_testAddHourlyEmployee.class,
	EmployeeServiceTest_addEmployeeTest.class
})
public class EmployeeServiceTestSuite {

}
