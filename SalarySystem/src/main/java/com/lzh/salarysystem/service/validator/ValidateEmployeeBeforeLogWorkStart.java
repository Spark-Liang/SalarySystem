package com.lzh.salarysystem.service.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lzh.salarysystem.entity.Employee;
import com.lzh.salarysystem.entity.WorkRecord;
import com.lzh.salarysystem.exception.HourlyEmloyeeHasBeenWorking;
import com.lzh.salarysystem.repository.WorkRecordRepository;
import com.lzh.salarysystem.service.Validator;

@Component
public class ValidateEmployeeBeforeLogWorkStart implements Validator<Employee>{
	
	private ValidateEmployeeIsHourlyEmployee validateEmployeeIsHourlyEmployee;
	
	@Autowired
	private WorkRecordRepository workRecordRepository;
	
	@Autowired
	public ValidateEmployeeBeforeLogWorkStart(ValidateEmployeeIsHourlyEmployee validateEmployeeIsHourlyEmployee) {
		super();
		this.validateEmployeeIsHourlyEmployee = validateEmployeeIsHourlyEmployee;
	}

	@Override
	public void validate(Employee employee) {
		validateEmployeeIsHourlyEmployee.validate(employee);
		WorkRecord currentWorkRecord = workRecordRepository.findCurrentWorkRecord(employee.getEmpID());
		if (isCurrentWorkRecord(currentWorkRecord)) {
			throw new HourlyEmloyeeHasBeenWorking();
		}
	}
	
	private boolean isCurrentWorkRecord(WorkRecord currentWorkRecord) {
		return currentWorkRecord != null 
				&& currentWorkRecord.isCurrentWorkRecord(); 
	}

}
