package com.lzh.salarysystem.service.validator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.lzh.salarysystem.entity.Employee;
import com.lzh.salarysystem.entity.HourlyEmployee;
import com.lzh.salarysystem.exception.EmployeeCantFoundById;
import com.lzh.salarysystem.exception.NotAnHourlyEmloyee;
import com.lzh.salarysystem.service.Validator;

@RunWith(MockitoJUnitRunner.class)
public class ValidateEmployeeIsHourlyEmployeeTest implements ValidatorTest<Employee>{
	
	private Validator<Employee> SUT = getSUT();
	
	@Override
	public Validator<Employee> getSUT() {
		return new ValidateEmployeeIsHourlyEmployee();
	}
	
	@Test(expected = EmployeeCantFoundById.class)
	public void should_throw_EmployeeCantNotFoundByID_when_cant_find_employee_by_id() {
		Employee employee = null;
		
		SUT.validate(employee);
	}
	
	@Test(expected = NotAnHourlyEmloyee.class)
	public void should_throw_NotAnHourlyEmloyee_when_the_given_ID_is_related_to_HourlyEmployee() {
		Integer empID = 1;
		Employee employee = new Employee(empID);
		
		SUT.validate(employee);
	}
	
	protected Employee buildSuitableEmployee(Integer empID) {
		Employee employee = new HourlyEmployee(empID);
		return employee;
	}
}
