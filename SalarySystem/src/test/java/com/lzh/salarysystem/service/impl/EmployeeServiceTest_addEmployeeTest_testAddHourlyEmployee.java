package com.lzh.salarysystem.service.impl;

import java.math.BigDecimal;

import org.junit.Test;

import com.lzh.salarysystem.domain.entity.Employee;
import com.lzh.salarysystem.domain.entity.HourlyEmployee;
import com.lzh.salarysystem.exception.HourlyRateNotAPositiveNumber;

public class EmployeeServiceTest_addEmployeeTest_testAddHourlyEmployee extends EmployeeServiceTest_addEmployeeTest{
	
	@Override
	protected Employee getTestedEmployee() {
		HourlyEmployee employee = new HourlyEmployee();
		employee.setHourlyRate(new BigDecimal(0.1));
		return employee;
	}
	
	@Test(expected = HourlyRateNotAPositiveNumber.class)
	public void hourlyRate_is_not_a_positive_number_when_hourlyRate_is_minus(){
		HourlyEmployee employee = proceeEmployeeToNormal(new HourlyEmployee());
		employee.setHourlyRate(new BigDecimal(-1.0));
		
		SUT.add(employee);
	}
	
	@Test(expected = HourlyRateNotAPositiveNumber.class)
	public void hourlyRate_is_not_a_positive_number_when_hourlyRate_is_zero(){
		HourlyEmployee employee = proceeEmployeeToNormal(new HourlyEmployee());
		employee.setHourlyRate(new BigDecimal(0));
		
		SUT.add(employee);
	}
}
