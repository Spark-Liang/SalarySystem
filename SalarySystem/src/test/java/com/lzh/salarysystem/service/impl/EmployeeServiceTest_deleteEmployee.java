package com.lzh.salarysystem.service.impl;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.lzh.salarysystem.domain.entity.Employee;
import com.lzh.salarysystem.exception.EmployeeCantFoundById;
import com.lzh.salarysystem.repository.EmployeeRepository;
import com.lzh.salarysystem.service.EmployeeService;
import com.lzh.salarysystem.service.impl.EmployeeServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest_deleteEmployee {

	@Mock
	protected EmployeeRepository employeeRepository;

	@InjectMocks
	protected EmployeeService SUT = new EmployeeServiceImpl();

	@Test
	public void can_delete_when_the_employee_can_be_found() {
		Integer empID = 1;
		Employee employeeInDB = new Employee(empID) {
			{
				setName("Test");
				setAddress("Test Address");
			}
		};
		when(employeeRepository.findOne(empID)).thenReturn(employeeInDB);
		
		SUT.delete(empID);
		
		verify(employeeRepository,times(1)).delete(employeeInDB);
	}

	@Test(expected = EmployeeCantFoundById.class)
	public void throw_Employee_id_is_Invalid_when_can_not_find_employee_by_id() {
		Integer empID = 1;
		when(employeeRepository.findOne(empID)).thenReturn(null);

		SUT.delete(empID);
	}

}
