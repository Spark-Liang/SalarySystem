package com.lzh.salarysystem.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.LocalTime;

import javax.transaction.Transactional;

import org.dbunit.dataset.IDataSet;
import org.dbunit.ext.mssql.InsertIdentityOperation;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lzh.salarysystem.SalarySystemITBaseTest;
import com.lzh.salarysystem.common.util.DBUnitEnvironment;
import com.lzh.salarysystem.entity.Employee;
import com.lzh.salarysystem.entity.HourlyEmployee;
import com.lzh.salarysystem.entity.WorkRecord;
import com.lzh.salarysystem.entity.WorkRecordInfo;
import com.lzh.salarysystem.ittest.ClassNameBasedXlsDataSetLoader;

@Component
public class WorkRecordRepositoryITTest extends SalarySystemITBaseTest{

	@Autowired
	private WorkRecordRepository SUT;
	
	@Test
	@Transactional
	public void testFindOne() throws Exception{
		DBUnitEnvironment environment = null;
		try {
			environment = dbUnitEnvironment.get();
			environment.backupCustom("Employee","WorkRecord");
			IDataSet initData = new ClassNameBasedXlsDataSetLoader()
					.loadDataSet(this.getClass(), "TestWorkRecordFindOne.xls");
			environment.execute(InsertIdentityOperation.CLEAN_INSERT, initData);
			Integer empID = 1;
			
			WorkRecord record = SUT.findTopByInfo_employee_idOrderByInfo_startTimeDesc(empID);
			
			WorkRecordInfo info = record.getInfo();
			Employee employee = info.getEmployee();
			assertTrue(employee instanceof HourlyEmployee);
			HourlyEmployee hourlyEmployee = (HourlyEmployee)employee;
			assertEquals(empID,hourlyEmployee.getId());
			assertEquals("test_1", hourlyEmployee.getName());
			assertEquals("test_address_1", employee.getAddress());
			assertEquals(new BigDecimal("100.0000"), hourlyEmployee.getHourlyRate());
			assertEquals(LocalTime.of(17, 0), info.getStartTime());
			assertEquals(null, info.getEndTime());
			
		} finally {
			environment.rollback();
		}
	}
	
	
}
