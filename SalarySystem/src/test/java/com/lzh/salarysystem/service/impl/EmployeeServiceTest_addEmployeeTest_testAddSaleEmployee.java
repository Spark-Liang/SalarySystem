package com.lzh.salarysystem.service.impl;

import java.math.BigDecimal;

import org.junit.Test;

import com.lzh.salarysystem.domain.entity.Employee;
import com.lzh.salarysystem.domain.entity.SaleEmployee;
import com.lzh.salarysystem.exception.CommissionRateNotAPositiveNumber;

public class EmployeeServiceTest_addEmployeeTest_testAddSaleEmployee extends EmployeeServiceTest_addEmployeeTest_testAddMonthlyEmployee{
	
	@Override
	protected Employee getTestedEmployee() {
		SaleEmployee employee = new SaleEmployee();
		proceeMonthlyEmployeeToNormal(employee);
		employee.setCommissionRate(new BigDecimal(0.1));
		return employee;
	}
	
	
	
	@Test(expected = CommissionRateNotAPositiveNumber.class)
	public void commissionRate_is_not_a_positive_number_when_hourlyRate_is_minus(){
		SaleEmployee employee = (SaleEmployee)getTestedEmployee();
		employee.setCommissionRate(new BigDecimal(-1.0));
		
		SUT.add(employee);
	}
	
	@Test(expected = CommissionRateNotAPositiveNumber.class)
	public void commissionRate_is_not_a_positive_number_when_hourlyRate_is_zero(){
		SaleEmployee employee = (SaleEmployee)getTestedEmployee();
		employee.setCommissionRate(new BigDecimal(0));
		
		SUT.add(employee);
	}
}
