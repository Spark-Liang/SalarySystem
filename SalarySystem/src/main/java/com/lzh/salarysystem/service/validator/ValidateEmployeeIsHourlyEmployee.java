package com.lzh.salarysystem.service.validator;

import com.lzh.salarysystem.entity.Employee;
import com.lzh.salarysystem.entity.HourlyEmployee;
import com.lzh.salarysystem.exception.EmployeeCantFoundById;
import com.lzh.salarysystem.exception.NotAnHourlyEmloyee;
import com.lzh.salarysystem.service.Validator;

public class ValidateEmployeeIsHourlyEmployee implements Validator<Employee>{

	@Override
	public void validate(Employee employee) {
		if(employee == null) {
			throw new EmployeeCantFoundById();
		}
		if (!(employee instanceof HourlyEmployee)) {
			throw new NotAnHourlyEmloyee();
		}
	}
	
}
