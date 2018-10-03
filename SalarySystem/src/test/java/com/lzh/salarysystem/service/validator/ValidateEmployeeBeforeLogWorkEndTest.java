package com.lzh.salarysystem.service.validator;

import static org.mockito.Mockito.when;

import java.time.LocalTime;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.lzh.salarysystem.domain.entity.Employee;
import com.lzh.salarysystem.domain.entity.HourlyEmployee;
import com.lzh.salarysystem.domain.entity.WorkRecord;
import com.lzh.salarysystem.domain.valueobject.WorkRecordInfo;
import com.lzh.salarysystem.exception.HourlyEmloyeeDoesNotWorking;
import com.lzh.salarysystem.service.Validator;
import com.lzh.salarysystem.service.WorkRecordService;

public class ValidateEmployeeBeforeLogWorkEndTest extends ValidateEmployeeIsHourlyEmployeeTest{
	@Mock
	protected WorkRecordService workRecordService;

	@InjectMocks
	private ValidateEmployeeBeforeLogWorkEnd SUT = (ValidateEmployeeBeforeLogWorkEnd) getSUT();
	
	@Override
	public Validator<Employee> getSUT() {
		return new ValidateEmployeeBeforeLogWorkEnd((ValidateEmployeeIsHourlyEmployee) super.getSUT());
	}
	
	@Test
	public void can_pass_validate_when_employee_meet_the_requirement() {
		Integer empID = 1;
		Employee employee = buildSuitableEmployee(empID);
		WorkRecord currentWorkRecord = new WorkRecord();
		WorkRecordInfo workRecordInfo = new WorkRecordInfo();
		LocalTime startTime = LocalTime.now();
		workRecordInfo.setStartTime(startTime);
		workRecordInfo.setEndTime(null);
		currentWorkRecord.setInfo(workRecordInfo);
		when(workRecordService.findCurrentWorkRecord(empID)).thenReturn(currentWorkRecord);
		
		SUT.validate(employee);
	}
	
	@Test(expected = HourlyEmloyeeDoesNotWorking.class)
	public void should_throw_HourlyEmloyeeDoesNotWorking_when_try_to_log_end_but_not_fount_record() {
		Integer empID = 1;
		Employee employee = buildSuitableEmployee(empID);
		when(workRecordService.findCurrentWorkRecord(empID)).thenReturn(null);
		
		SUT.validate(employee);
	}
	
	@Test(expected = HourlyEmloyeeDoesNotWorking.class)
	public void should_throw_HourlyEmloyeeDoesNotWorking_when_try_to_log_end_but_found_record_is_finish() {
		Integer empID = 1;
		Employee employee = buildSuitableEmployee(empID);
		WorkRecord currentWorkRecord = new WorkRecord();
		WorkRecordInfo workRecordInfo = new WorkRecordInfo();
		LocalTime startTime = LocalTime.now();
		workRecordInfo.setEmployee((HourlyEmployee) employee);
		workRecordInfo.setStartTime(startTime);
		workRecordInfo.setEndTime(startTime.plusHours(8));
		currentWorkRecord.setInfo(workRecordInfo);
		when(workRecordService.findCurrentWorkRecord(empID)).thenReturn(currentWorkRecord);
		
		SUT.validate(employee);
	}
}
