package com.lzh.salarysystem.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.security.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.mockito.InjectMocks;

import com.lzh.salarysystem.common.util.DateUtils;
import com.lzh.salarysystem.entity.HourlyEmployee;
import com.lzh.salarysystem.entity.TimeCard;
import com.lzh.salarysystem.entity.WorkRecord;
import com.lzh.salarysystem.entity.WorkRecordInfo;

public class WorkRecordServiceImplTest_calculateTimeCardsFromWorkRecords {
	
	@InjectMocks
	private WorkRecordServiceImpl SUT = new WorkRecordServiceImpl();
	
	@Test
	public void should_return_a_timeCard_when_an_employee_with_one_record(){
		
		Date workDate = getDayFromString("2018-01-01");
		Integer hours = 2;
		HourlyEmployee employee = buildSimpleHourlyEmployeeWithIdAndRate(1,0.1);
		WorkRecordInfo sourceWorkRecordInfo = new WorkRecordInfo();
		sourceWorkRecordInfo.setEmployee(employee);
		Date startTime = DateUtils.addMinutes(workDate, 1);
		sourceWorkRecordInfo.setStartTime(startTime);
		sourceWorkRecordInfo.setEndTime(DateUtils.addHours(startTime, hours));
		Date endTime = DateUtils.addHours(startTime, hours);
		
		assertEquals(hours.intValue(), DateUtils.sub(endTime, startTime).getHours());
		WorkRecord sourceWorkRecord = new WorkRecord();
		sourceWorkRecord.setWorkRecordInfo(sourceWorkRecordInfo);
		List<WorkRecord> sourceRecords = Arrays.asList(sourceWorkRecord);
		
		Collection<TimeCard> timeCards = SUT.calculateTimeCardsFromWorkRecords(sourceRecords, workDate);
		TimeCard result = timeCards.iterator().next();
		assertEquals(result.getEmployee(),employee);
		assertTrue(DateUtils.isSameDay(result.getWorkDate(), workDate));
		assertEquals(hours, result.getHours());
	}
	
	private WorkRecord buildRecordWhichEmployeDoesNotFinishWork(Date workDate) {
		HourlyEmployee employee = buildSimpleHourlyEmployeeWithIdAndRate(1,0.1);
		WorkRecordInfo workRecordInfo = new WorkRecordInfo();
		workRecordInfo.setEmployee(employee);
		workRecordInfo.setStartTime(DateUtils.addMinutes(workDate, 1));
		workRecordInfo.setStartTime(null);
		WorkRecord workRecord = new WorkRecord();
		workRecord.setWorkRecordInfo(workRecordInfo);
		return workRecord;
	}

	private Collection<? extends WorkRecord> buildRecordWhichEmployeHasTwoRecord(Date workDate) {
		List<WorkRecord> results = new LinkedList<>();
		HourlyEmployee employee = buildSimpleHourlyEmployeeWithIdAndRate(1,0.1);
		WorkRecordInfo workRecordInfo1 = new WorkRecordInfo();
		workRecordInfo1.setEmployee(employee);
		workRecordInfo1.setStartTime(DateUtils.addMinutes(workDate, 1));
		workRecordInfo1.setStartTime(DateUtils.addMinutes(workDate, 2));
		WorkRecord workRecord1 = new WorkRecord();
		workRecord1.setWorkRecordInfo(workRecordInfo1);
		results.add(workRecord1);
		WorkRecordInfo workRecordInfo2 = new WorkRecordInfo();
		workRecordInfo2.setEmployee(employee);
		workRecordInfo2.setStartTime(DateUtils.addMinutes(workDate, 3));
		workRecordInfo2.setStartTime(DateUtils.addMinutes(workDate, 4));
		WorkRecord workRecord2 = new WorkRecord();
		workRecord1.setWorkRecordInfo(workRecordInfo2);
		results.add(workRecord2);
		return results;
	}

	private WorkRecord buildRecordWhichEmployeHasOneRecord(Date workDate) {
		HourlyEmployee employee = buildSimpleHourlyEmployeeWithIdAndRate(1,0.1);
		WorkRecordInfo workRecordInfo = new WorkRecordInfo();
		workRecordInfo.setEmployee(employee);
		workRecordInfo.setStartTime(DateUtils.addMinutes(workDate, 1));
		workRecordInfo.setStartTime(DateUtils.addMinutes(workDate, 2));
		WorkRecord workRecord = new WorkRecord();
		workRecord.setWorkRecordInfo(workRecordInfo);
		return workRecord;
	}
	
	private HourlyEmployee buildSimpleHourlyEmployeeWithIdAndRate(int empID, double rate) {
		HourlyEmployee employee = new HourlyEmployee(empID);
		employee.setName("test_" + empID);
		employee.setAddress("testAddress_" + empID);
		employee.setHourlyRate(new BigDecimal(rate));
		return employee;
	}
	
	private Date getDayFromString(String str) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
}
