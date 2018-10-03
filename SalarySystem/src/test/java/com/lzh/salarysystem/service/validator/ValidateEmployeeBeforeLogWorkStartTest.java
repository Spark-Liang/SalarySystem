package com.lzh.salarysystem.service.validator;

import static org.mockito.Mockito.when;

import java.time.LocalTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.lzh.salarysystem.domain.entity.Employee;
import com.lzh.salarysystem.domain.entity.HourlyEmployee;
import com.lzh.salarysystem.domain.entity.WorkRecord;
import com.lzh.salarysystem.domain.valueobject.WorkRecordInfo;
import com.lzh.salarysystem.exception.HourlyEmloyeeHasBeenWorking;
import com.lzh.salarysystem.service.Validator;
import com.lzh.salarysystem.service.WorkRecordService;

@RunWith(MockitoJUnitRunner.class)
public class ValidateEmployeeBeforeLogWorkStartTest extends ValidateEmployeeIsHourlyEmployeeTest{
	
	@Mock
	protected WorkRecordService workRecordService;

	@InjectMocks
	private ValidateEmployeeBeforeLogWorkStart SUT = (ValidateEmployeeBeforeLogWorkStart) getSUT();
	
	@Override
	public Validator<Employee> getSUT() {
		return new ValidateEmployeeBeforeLogWorkStart((ValidateEmployeeIsHourlyEmployee) super.getSUT());
	}
	
	@Test
	public void can_pass_validate_when_employee_has_no_workrecord() {
		Integer empID = 1;
		Employee employee = buildSuitableEmployee(empID);
		when(workRecordService.findCurrentWorkRecord(empID)).thenReturn(null);
		
		SUT.validate(employee);
	}
	
	@Test
	public void can_pass_validate_when_last_workrecord_of_the_employee_is_finish() {
		Integer empID = 1;
		Employee employee = buildSuitableEmployee(empID);
		WorkRecord currentWorkRecord = new WorkRecord();
		WorkRecordInfo currentWorkRecordInfo = new WorkRecordInfo();
		currentWorkRecordInfo.setEmployee(new HourlyEmployee(empID));
		LocalTime startTime = LocalTime.now();
		currentWorkRecordInfo.setStartTime(startTime);
		currentWorkRecordInfo.setEndTime(startTime.plusHours(8));
		currentWorkRecord.setInfo(currentWorkRecordInfo);
		when(workRecordService.findCurrentWorkRecord(empID)).thenReturn(currentWorkRecord);
		
		SUT.validate(employee);
	}
	
	@Test(expected = HourlyEmloyeeHasBeenWorking.class)
	public void should_throw_HourlyEmloyeeHasBeenWorking_when_a_working_employee_try_to_log_start(){
		Integer empID = 1;
		Employee employee = buildSuitableEmployee(empID);
		WorkRecord currentWorkRecord = new WorkRecord();
		WorkRecordInfo currentWorkRecordInfo = new WorkRecordInfo();
		currentWorkRecordInfo.setEmployee(new HourlyEmployee(empID));
		LocalTime startTime = LocalTime.now();
		currentWorkRecordInfo.setStartTime(startTime);
		currentWorkRecordInfo.setEndTime(null);
		currentWorkRecord.setInfo(currentWorkRecordInfo);
		when(workRecordService.findCurrentWorkRecord(empID)).thenReturn(currentWorkRecord);
		
		SUT.validate(employee);
	}



	
	
	
}
