package com.lzh.salarysystem.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.lzh.salarysystem.common.util.ValidatorHelper;
import com.lzh.salarysystem.entity.Employee;
import com.lzh.salarysystem.entity.HourlyEmployee;
import com.lzh.salarysystem.entity.WorkRecord;
import com.lzh.salarysystem.entity.WorkRecordInfo;
import com.lzh.salarysystem.repository.EmployeeRepository;
import com.lzh.salarysystem.repository.WorkRecordRepository;
import com.lzh.salarysystem.service.WorkRecordService;
import com.lzh.salarysystem.service.validator.ValidateEmployeeBeforeLogWorkStart;

@RunWith(MockitoJUnitRunner.class)
public class WorkRecordService_logWorkStart {

	@Mock
	protected WorkRecordRepository workRecordRepository;

	@Mock
	protected EmployeeRepository employeeRepository;
	
	@Mock
	protected ValidatorHelper validatorHelper;
	
	@Mock
	protected ValidateEmployeeBeforeLogWorkStart validator;
	
	@InjectMocks
	protected WorkRecordService SUT = new WorkRecordServiceImpl();
	
	@Captor
	public ArgumentCaptor<WorkRecord> workRecordCaptor;
	
	@Before
	public void setUp() {
		when(validatorHelper.getValidator(ValidateEmployeeBeforeLogWorkStart.class)).thenReturn(validator);
	}
	
	@Test
	public void can_log_work_start_when_given_the_right_empID(){
		Integer empID = 1;
		Employee employeeInDB = new HourlyEmployee(empID);
		when(employeeRepository.findOne(empID)).thenReturn(employeeInDB);
		doNothing().when(validator).validate(employeeInDB);
		
		SUT.logWorkStart(empID);
		
		verify(workRecordRepository,times(1)).save(workRecordCaptor.capture());
		WorkRecordInfo infoOfWorkRecordToSave = workRecordCaptor.getAllValues().get(0).getWorkRecordInfo();
		assertEquals(empID, infoOfWorkRecordToSave.getEmployee().getEmpID());
		assertNotNull(infoOfWorkRecordToSave.getStartTime());
		assertNull(infoOfWorkRecordToSave.getEndTime());
	}
	
	@Test
	public void cant_log_work_start_when_employee_cant_pass_validate() {
		Integer empID = 1;
		Employee employeeInDB = new HourlyEmployee(empID);
		when(employeeRepository.findOne(empID)).thenReturn(employeeInDB);
		doThrow(Exception.class).when(validator).validate(employeeInDB);
		
		try {
			SUT.logWorkStart(empID);
		}catch (Exception e) {}
		finally {
			verify(workRecordRepository,times(0)).save(workRecordCaptor.capture());
		} 
	}
}
