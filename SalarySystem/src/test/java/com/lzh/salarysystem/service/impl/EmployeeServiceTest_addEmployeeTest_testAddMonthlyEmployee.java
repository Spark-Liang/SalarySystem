package com.lzh.salarysystem.service.impl;

import java.math.BigDecimal;

import org.junit.Test;

import com.lzh.salarysystem.entity.Employee;
import com.lzh.salarysystem.entity.MonthlyEmployee;
import com.lzh.salarysystem.exception.MonthlySalaryNotAPositiveNumber;

public class EmployeeServiceTest_addEmployeeTest_testAddMonthlyEmployee extends EmployeeServiceTest_addEmployeeTest{
	
	@Override
	protected Employee getTestedEmployee() {
		MonthlyEmployee employee = new MonthlyEmployee();
		proceeMonthlyEmployeeToNormal(employee);
		return employee;
	}

	protected void proceeMonthlyEmployeeToNormal(MonthlyEmployee employee) {
		employee.setMonthlySalary(new BigDecimal(0.1));
		proceeEmployeeToNormal(employee);
	}
	
	@Test(expected = MonthlySalaryNotAPositiveNumber.class)
	public void hourlyRate_is_not_a_positive_number_when_hourlyRate_is_minus(){
		MonthlyEmployee employee = proceeEmployeeToNormal(new MonthlyEmployee());
		employee.setMonthlySalary(new BigDecimal(-1.0));
		
		SUT.add(employee);
	}
	
	@Test(expected = MonthlySalaryNotAPositiveNumber.class)
	public void hourlyRate_is_not_a_positive_number_when_hourlyRate_is_zero(){
		MonthlyEmployee employee = proceeEmployeeToNormal(new MonthlyEmployee());
		employee.setMonthlySalary(new BigDecimal(0));
		
		SUT.add(employee);
	}
}
