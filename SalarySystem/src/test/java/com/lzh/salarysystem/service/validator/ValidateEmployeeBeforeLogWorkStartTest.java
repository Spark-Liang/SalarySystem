package com.lzh.salarysystem.service.validator;

import static org.mockito.Mockito.when;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.lzh.salarysystem.entity.Employee;
import com.lzh.salarysystem.entity.HourlyEmployee;
import com.lzh.salarysystem.entity.WorkRecord;
import com.lzh.salarysystem.exception.HourlyEmloyeeHasBeenWorking;
import com.lzh.salarysystem.repository.WorkRecordRepository;
import com.lzh.salarysystem.service.Validator;

@RunWith(MockitoJUnitRunner.class)
public class ValidateEmployeeBeforeLogWorkStartTest extends ValidateEmployeeIsHourlyEmployeeTest{
	
	@Mock
	protected WorkRecordRepository workRecordRepository;

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
		when(workRecordRepository.findCurrentWorkRecord(empID)).thenReturn(null);
		
		SUT.validate(employee);
	}
	
	@Test
	public void can_pass_validate_when_last_workrecord_of_the_employee_is_finish() {
		Integer empID = 1;
		Employee employee = buildSuitableEmployee(empID);
		WorkRecord currentWorkRecord = new WorkRecord();
		Date currentDate = new Date();
		currentWorkRecord.getWorkRecordInfo().setEmployee((HourlyEmployee) employee);
		currentWorkRecord.getWorkRecordInfo().setStartTime(currentDate);
		currentWorkRecord.getWorkRecordInfo().setEndTime(DateUtils.addHours(currentDate, 8));
		when(workRecordRepository.findCurrentWorkRecord(empID)).thenReturn(currentWorkRecord);
		
		SUT.validate(employee);
	}
	
	@Test(expected = HourlyEmloyeeHasBeenWorking.class)
	public void should_throw_HourlyEmloyeeHasBeenWorking_when_a_working_employee_try_to_log_start(){
		Integer empID = 1;
		Employee employee = buildSuitableEmployee(empID);
		WorkRecord currentWorkRecord = new WorkRecord();
		currentWorkRecord.getWorkRecordInfo().setEmployee(new HourlyEmployee(empID));
		Date currentDatetime = new Date();
		currentWorkRecord.getWorkRecordInfo().setStartTime(currentDatetime);
		currentWorkRecord.getWorkRecordInfo().setEndTime(null);
		when(workRecordRepository.findCurrentWorkRecord(empID)).thenReturn(currentWorkRecord);
		
		SUT.validate(employee);
	}



	
	
	
}
