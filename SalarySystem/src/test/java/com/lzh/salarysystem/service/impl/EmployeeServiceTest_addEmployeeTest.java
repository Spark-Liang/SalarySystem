package com.lzh.salarysystem.service.impl;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.lzh.salarysystem.common.util.ValidatorHelper;
import com.lzh.salarysystem.domain.entity.Employee;
import com.lzh.salarysystem.exception.AddressIsNull;
import com.lzh.salarysystem.exception.AddressIsTooLong;
import com.lzh.salarysystem.exception.NameIsNull;
import com.lzh.salarysystem.exception.NameIsTooLong;
import com.lzh.salarysystem.repository.EmployeeRepository;
import com.lzh.salarysystem.service.EmployeeService;
import com.lzh.salarysystem.service.impl.EmployeeServiceImpl;
import com.lzh.salarysystem.service.validator.ValidateEmployeeBeforeAdd;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest_addEmployeeTest {
	@Mock
	protected EmployeeRepository employeeRepository;

	@Mock
	protected ValidatorHelper validatorHelper;
	
	@InjectMocks
	protected EmployeeService SUT = new EmployeeServiceImpl();
	
	protected Employee getTestedEmployee() {
		return new Employee();
	}
	
	@Before
	public void setUp() {
		when(validatorHelper.getValidator(ValidateEmployeeBeforeAdd.class)).thenReturn(new ValidateEmployeeBeforeAdd());
	}
	
	
	@Test
	public void can_add_employee() {
		Employee employee = buildNormalEmployee();
		
		SUT.add(employee);
		
		verify(employeeRepository).save(employee);
	}

	@Test(expected = NameIsNull.class)
	public void throw_name_is_null_when_its_name_is_null(){
		Employee employee = buildNormalEmployee();
		employee.setName(null);
		
		SUT.add(employee);
	}
	
	@Test(expected = NameIsTooLong.class)
	public void throw_name_is_to_long_when_the_length_of_its_name_is_larger_than_32(){
		Employee employee = buildNormalEmployee();
		employee.setName("looooooooooooooooooooooooooooooooooooooooooooooooong name");
		
		SUT.add(employee);
	}
	
	@Test(expected = AddressIsNull.class)
	public void throw_address_is_null_when_its_address_is_null(){
		Employee employee = buildNormalEmployee();
		employee.setAddress(null);
		
		SUT.add(employee);
	}
	
	@Test(expected = AddressIsTooLong.class)
	public void throw_address_is_to_long_when_the_length_of_its_address_is_larger_than_1024(){
		Employee employee = buildNormalEmployee();
		char[] addressCharArr = new char[Employee.MAX_ADDRESS_LENGTH + 1];
		Arrays.fill(addressCharArr, 'a');
		employee.setAddress(new String(addressCharArr));
		
		SUT.add(employee);
	}
	
	protected Employee buildNormalEmployee() {
		Employee employee = getTestedEmployee();
		proceeEmployeeToNormal(employee);
		return employee;
	}
	
	protected <T extends Employee> T proceeEmployeeToNormal(T employee) {
		employee.setName("test");
		employee.setAddress("test address");
		return employee;
	}
	
}
