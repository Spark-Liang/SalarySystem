package com.lzh.salarysystem.service.validator;

import static org.mockito.Mockito.when;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.lzh.salarysystem.entity.Employee;
import com.lzh.salarysystem.entity.HourlyEmployee;
import com.lzh.salarysystem.entity.WorkRecord;
import com.lzh.salarysystem.entity.WorkRecordInfo;
import com.lzh.salarysystem.exception.HourlyEmloyeeDoesNotWorking;
import com.lzh.salarysystem.repository.WorkRecordRepository;
import com.lzh.salarysystem.service.Validator;

public class ValidateEmployeeBeforeLogWorkEndTest extends ValidateEmployeeIsHourlyEmployeeTest{
	@Mock
	protected WorkRecordRepository workRecordRepository;

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
		Date currentDate = new Date();
		workRecordInfo.setStartTime(currentDate);
		workRecordInfo.setEndTime(null);
		currentWorkRecord.setWorkRecordInfo(workRecordInfo);
		when(workRecordRepository.findCurrentWorkRecord(empID)).thenReturn(currentWorkRecord);
		
		SUT.validate(employee);
	}
	
	@Test(expected = HourlyEmloyeeDoesNotWorking.class)
	public void should_throw_HourlyEmloyeeDoesNotWorking_when_try_to_log_end_but_not_fount_record() {
		Integer empID = 1;
		Employee employee = buildSuitableEmployee(empID);
		when(workRecordRepository.findCurrentWorkRecord(empID)).thenReturn(null);
		
		SUT.validate(employee);
	}
	
	@Test(expected = HourlyEmloyeeDoesNotWorking.class)
	public void should_throw_HourlyEmloyeeDoesNotWorking_when_try_to_log_end_but_found_record_is_finish() {
		Integer empID = 1;
		Employee employee = buildSuitableEmployee(empID);
		WorkRecord currentWorkRecord = new WorkRecord();
		WorkRecordInfo workRecordInfo = new WorkRecordInfo();
		Date currentDate = new Date();
		workRecordInfo.setEmployee((HourlyEmployee) employee);
		workRecordInfo.setStartTime(currentDate);
		workRecordInfo.setEndTime(DateUtils.addHours(currentDate, 8));
		currentWorkRecord.setWorkRecordInfo(workRecordInfo);
		when(workRecordRepository.findCurrentWorkRecord(empID)).thenReturn(currentWorkRecord);
		
		SUT.validate(employee);
	}
}
