package com.lzh.salarysystem.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lzh.salarysystem.SalarySystemITBaseTest;
import com.lzh.salarysystem.domain.entity.Employee;
import com.lzh.salarysystem.domain.entity.HourlyEmployee;
import com.lzh.salarysystem.domain.entity.MonthlyEmployee;
import com.lzh.salarysystem.domain.entity.SaleEmployee;

@Component
public class EmployeeRepositoryITTest extends SalarySystemITBaseTest{
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Test
	//@DatabaseSetup("EmployeeRepositoryITTestData.xml")
	public void TestGetMultiTypeEmployee() {
		Employee employee = employeeRepository.findOne(1);
		assertTrue(employee instanceof Employee);
		assertEquals("Test_1", employee.getName());
		assertEquals("Test_address", employee.getAddress());
		employee = employeeRepository.findOne(2);
		assertTrue(employee instanceof HourlyEmployee);
		HourlyEmployee hourlyEmployee = (HourlyEmployee)employee;
		assertEquals("Test_2", hourlyEmployee.getName());
		assertEquals("Test_address", hourlyEmployee.getAddress());
		assertEquals(new BigDecimal("0.1000"), hourlyEmployee.getHourlyRate());
		
		employee = employeeRepository.findOne(3);
		assertTrue(employee instanceof MonthlyEmployee);
		MonthlyEmployee monthlyEmployee = (MonthlyEmployee)employee;
		assertEquals("Test_3", monthlyEmployee.getName());
		assertEquals("Test_address", monthlyEmployee.getAddress());
		assertEquals(new BigDecimal("7000.0000"), monthlyEmployee.getMonthlySalary());
		
		employee = employeeRepository.findOne(4);
		assertTrue(employee instanceof SaleEmployee);
		SaleEmployee saleEmployee = (SaleEmployee)employee;
		assertEquals("Test_4", saleEmployee.getName());
		assertEquals("Test_address", saleEmployee.getAddress());
		assertEquals(new BigDecimal("6000.0000"), saleEmployee.getMonthlySalary());
		assertEquals(new BigDecimal("0.1000"), saleEmployee.getCommissionRate());
	}
	
}
