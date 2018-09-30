package com.lzh.salarysystem.service.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lzh.salarysystem.entity.Employee;
import com.lzh.salarysystem.entity.WorkRecord;
import com.lzh.salarysystem.exception.HourlyEmloyeeDoesNotWorking;
import com.lzh.salarysystem.service.Validator;
import com.lzh.salarysystem.service.WorkRecordService;

@Component
public class ValidateEmployeeBeforeLogWorkEnd implements Validator<Employee> {
	
	private ValidateEmployeeIsHourlyEmployee validateEmployeeIsHourlyEmployee;
	
	@Autowired
	private WorkRecordService workRecordService;
	
	@Autowired
	public ValidateEmployeeBeforeLogWorkEnd(ValidateEmployeeIsHourlyEmployee validateEmployeeIsHourlyEmployee) {
		super();
		this.validateEmployeeIsHourlyEmployee = validateEmployeeIsHourlyEmployee;
	}



	@Override
	public void validate(Employee employee) {
		validateEmployeeIsHourlyEmployee.validate(employee);
		validateHasCurrentRecord(employee);
	}



	private void validateHasCurrentRecord(Employee employee) {
		WorkRecord currentWorkRecord = workRecordService.findCurrentWorkRecord(employee.getId());
		if(currentWorkRecord == null || !currentWorkRecord.isCurrentWorkRecord()) {
			throw new HourlyEmloyeeDoesNotWorking();
		}
	}

}
