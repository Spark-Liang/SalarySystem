package com.lzh.salarysystem.service.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lzh.salarysystem.entity.Employee;
import com.lzh.salarysystem.entity.WorkRecord;
import com.lzh.salarysystem.exception.HourlyEmloyeeHasBeenWorking;
import com.lzh.salarysystem.service.Validator;
import com.lzh.salarysystem.service.WorkRecordService;

@Component
public class ValidateEmployeeBeforeLogWorkStart implements Validator<Employee>{
	
	private ValidateEmployeeIsHourlyEmployee validateEmployeeIsHourlyEmployee;
	
	@Autowired
	private WorkRecordService workRecordService;
	
	@Autowired
	public ValidateEmployeeBeforeLogWorkStart(ValidateEmployeeIsHourlyEmployee validateEmployeeIsHourlyEmployee) {
		super();
		this.validateEmployeeIsHourlyEmployee = validateEmployeeIsHourlyEmployee;
	}

	@Override
	public void validate(Employee employee) {
		validateEmployeeIsHourlyEmployee.validate(employee);
		WorkRecord currentWorkRecord = workRecordService.findCurrentWorkRecord(employee.getId());
		if (isCurrentWorkRecord(currentWorkRecord)) {
			throw new HourlyEmloyeeHasBeenWorking();
		}
	}
	
	private boolean isCurrentWorkRecord(WorkRecord currentWorkRecord) {
		return currentWorkRecord != null 
				&& currentWorkRecord.isCurrentWorkRecord(); 
	}

}
