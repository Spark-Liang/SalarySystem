package com.lzh.salarysystem.service.impl;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.mockito.InjectMocks;

import com.lzh.salarysystem.domain.entity.HourlyEmployee;
import com.lzh.salarysystem.domain.entity.TimeCard;
import com.lzh.salarysystem.domain.entity.WorkRecord;
import com.lzh.salarysystem.domain.valueobject.WorkRecordInfo;

public class WorkRecordServiceImplTest_calculateTimeCardsFromWorkRecords {
	
	@InjectMocks
	private WorkRecordServiceImpl SUT = new WorkRecordServiceImpl();
	
	@Test
	public void should_return_a_timeCard_when_an_employee_with_one_record(){
		LocalDate workDate = LocalDate.of(2018, 9, 20);
		LocalTime startTime = LocalTime.of(8, 0);
		Integer hours = 2;
		HourlyEmployee employee = buildSimpleHourlyEmployeeWithIdAndRate(1,0.1);
		WorkRecord sourceWorkRecord = buildRecordWithStartTimeWorkHoursAndEmployee(startTime, hours, employee);
		List<WorkRecord> sourceRecords = Arrays.asList(sourceWorkRecord);
		
		Collection<TimeCard> timeCards = SUT.calculateTimeCardsFromWorkRecords(sourceRecords, workDate);
		
		TimeCard result = timeCards.iterator().next();
		assertEquals(result.getEmployee(),employee);
		assertEquals(result.getWorkDate(), workDate);
		assertEquals(hours, result.getHours());
	}

	
	@Test
	public void should_return_a_timeCard_when_an_employee_with_two_record(){
		LocalDate workDate = LocalDate.of(2018, 9, 20);
		LocalTime startTime = LocalTime.of(8, 0);
		Integer hours_first = 2
				,hours_second = 4;
		HourlyEmployee employee = buildSimpleHourlyEmployeeWithIdAndRate(1,0.1);
		List<WorkRecord> sourceRecords = Arrays.asList(
				buildRecordWithStartTimeWorkHoursAndEmployee(startTime, hours_first, employee)
				,buildRecordWithStartTimeWorkHoursAndEmployee(startTime, hours_second, employee)
				);
		
		Collection<TimeCard> timeCards = SUT.calculateTimeCardsFromWorkRecords(sourceRecords, workDate);
		TimeCard result = timeCards.iterator().next();
		assertEquals(result.getEmployee(),employee);
		assertEquals(result.getWorkDate(), workDate);
		assertEquals(hours_first + hours_second, result.getHours().intValue());
	}
	
	@Test
	public void should_return_a_timeCard_when_an_employee_with_unfinish_record(){
		LocalDate workDate = LocalDate.of(2018, 9, 20);
		LocalTime startTime = LocalTime.of(8, 0);
		Integer hours_first = 2;
		HourlyEmployee employee = buildSimpleHourlyEmployeeWithIdAndRate(1,0.1);
		WorkRecord workRecord = buildRecordWithStartTimeWorkHoursAndEmployee(startTime, hours_first, employee);
		workRecord.getInfo().setEndTime(null);
		List<WorkRecord> sourceRecords = Arrays.asList(workRecord);
		
		Collection<TimeCard> timeCards = SUT.calculateTimeCardsFromWorkRecords(sourceRecords, workDate);
		TimeCard result = timeCards.iterator().next();
		assertEquals(result.getEmployee(),employee);
		assertEquals(result.getWorkDate(), workDate);
		assertEquals(0, result.getHours().intValue());
	}

	
	private HourlyEmployee buildSimpleHourlyEmployeeWithIdAndRate(int empID, double rate) {
		HourlyEmployee employee = new HourlyEmployee(empID);
		employee.setName("test_" + empID);
		employee.setAddress("testAddress_" + empID);
		employee.setHourlyRate(new BigDecimal(rate));
		return employee;
	}
	
	private WorkRecord buildRecordWithStartTimeWorkHoursAndEmployee(LocalTime startTime, Integer hours,
			HourlyEmployee employee) {
		WorkRecordInfo sourceWorkRecordInfo = new WorkRecordInfo();
		sourceWorkRecordInfo.setEmployee(employee);
		sourceWorkRecordInfo.setStartTime(startTime);
		sourceWorkRecordInfo.setEndTime(startTime.plusHours(hours));
		WorkRecord sourceWorkRecord = new WorkRecord();
		sourceWorkRecord.setInfo(sourceWorkRecordInfo);
		return sourceWorkRecord;
	}

}
