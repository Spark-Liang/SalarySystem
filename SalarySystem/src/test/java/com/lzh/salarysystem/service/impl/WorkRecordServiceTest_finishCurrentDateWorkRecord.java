package com.lzh.salarysystem.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.lzh.salarysystem.domain.entity.HourlyEmployee;
import com.lzh.salarysystem.domain.entity.TimeCard;
import com.lzh.salarysystem.domain.entity.WorkRecord;
import com.lzh.salarysystem.domain.entity.WorkRecordHist;
import com.lzh.salarysystem.domain.valueobject.WorkRecordInfo;
import com.lzh.salarysystem.repository.TimeCardRepository;
import com.lzh.salarysystem.repository.WorkRecordHistRepository;
import com.lzh.salarysystem.repository.WorkRecordRepository;
import com.lzh.salarysystem.service.WorkRecordService;

@RunWith(MockitoJUnitRunner.class)
public class WorkRecordServiceTest_finishCurrentDateWorkRecord {

	@Mock
	private TimeCardRepository timeCardRepository;
	
	@Mock
	private WorkRecordRepository workRecordRepository;
	
	@Mock
	private WorkRecordHistRepository workRecordHistRepository;
	
	@InjectMocks
	private WorkRecordService SUT = new WorkRecordServiceImpl();
	
	@Captor
	private ArgumentCaptor<Iterable<TimeCard>> timeCardsToSaveCaptor;
	
	@Captor
	private ArgumentCaptor<List<WorkRecord>> workRecordsToBeDelete;
	
	@Captor
	private ArgumentCaptor<Iterable<WorkRecordHist>> workRecordHistToBeSave;
	
	@Test
	public void should_finish_all_record_and_generate_timecard_and_remove_record_from_current_to_history() {
		LocalDate workDate = LocalDate.now();
		List<WorkRecord> workRecordsInDB = getTestWorkRecords(workDate);
		when(workRecordRepository.findAll()).thenReturn(workRecordsInDB);
		List<WorkRecordInfo> sourceRecordInfos = 
				workRecordsInDB.stream()
				.map(WorkRecord::getInfo)
				.collect(Collectors.toList());
		
		Collection<TimeCard> timeCards = ((WorkRecordServiceImpl)SUT).calculateTimeCardsFromWorkRecords(workRecordsInDB,workDate);
		
		SUT.finishCurrentDateWorkRecord(workDate);
		
		verify(timeCardRepository,times(1)).save(timeCardsToSaveCaptor.capture());
		List<TimeCard> timeCardsToBeSave = new LinkedList<>();
		timeCardsToSaveCaptor.getValue().forEach(e -> timeCardsToBeSave.add(e));
		assertEquals(timeCards,timeCardsToBeSave);
		verify(workRecordHistRepository,times(1)).save(workRecordHistToBeSave.capture());
		List<WorkRecordInfo> listOfWorkRecordInfoToBeSave = new LinkedList<>();
		workRecordHistToBeSave.getValue().forEach(e -> listOfWorkRecordInfoToBeSave.add(e.getInfo()));
		assertEquals(sourceRecordInfos, listOfWorkRecordInfoToBeSave);
		verify(workRecordRepository,times(1)).deleteAll();
	}

	private List<WorkRecord> getTestWorkRecords(LocalDate workDate) {
		List<WorkRecord> results = new LinkedList<>();
		results.add(buildRecordWhichEmployeHasOneRecord(workDate));
		results.addAll(buildRecordWhichEmployeHasTwoRecord(workDate));
		results.add(buildRecordWhichEmployeDoesNotFinishWork(workDate));
		return results;
	}

	private WorkRecord buildRecordWhichEmployeDoesNotFinishWork(LocalDate workDate) {
		HourlyEmployee employee = buildSimpleHourlyEmployeeWithIdAndRate(1,0.1);
		LocalTime startTime = LocalTime.of(8, 0);
		Integer hours_first = 2;
		WorkRecord workRecord = buildRecordWithStartTimeWorkHoursAndEmployee(startTime, hours_first, employee);
		workRecord.getInfo().setEndTime(null);
		return workRecord;
	}

	private Collection<? extends WorkRecord> buildRecordWhichEmployeHasTwoRecord(LocalDate workDate) {
		LocalTime startTime = LocalTime.of(8, 0);
		Integer hours_first = 2
				,hours_second = 4;
		HourlyEmployee employee = buildSimpleHourlyEmployeeWithIdAndRate(1,0.1);
		return Arrays.asList(
				buildRecordWithStartTimeWorkHoursAndEmployee(startTime, hours_first, employee)
				,buildRecordWithStartTimeWorkHoursAndEmployee(startTime, hours_second, employee)
				);
	}

	private WorkRecord buildRecordWhichEmployeHasOneRecord(LocalDate workDate) {
		LocalTime startTime = LocalTime.of(8, 0);
		Integer hours = 2;
		HourlyEmployee employee = buildSimpleHourlyEmployeeWithIdAndRate(1,0.1);
		return buildRecordWithStartTimeWorkHoursAndEmployee(startTime, hours, employee);
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
	
	private HourlyEmployee buildSimpleHourlyEmployeeWithIdAndRate(int empID, double rate) {
		HourlyEmployee employee = new HourlyEmployee(empID);
		employee.setName("test_" + empID);
		employee.setAddress("testAddress_" + empID);
		employee.setHourlyRate(new BigDecimal(rate));
		return employee;
	}
}
