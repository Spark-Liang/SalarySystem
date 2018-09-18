package com.lzh.salarysystem.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.lzh.salarysystem.common.util.DateUtils;
import com.lzh.salarysystem.entity.HourlyEmployee;
import com.lzh.salarysystem.entity.HourlyEmployee;
import com.lzh.salarysystem.entity.TimeCard;
import com.lzh.salarysystem.entity.WorkRecord;
import com.lzh.salarysystem.entity.WorkRecordHist;
import com.lzh.salarysystem.entity.WorkRecordInfo;
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
		Date workDate = new Date();
		List<WorkRecord> workRecordsInDB = getTestWorkRecords(workDate);
		List<WorkRecordInfo> sourceRecordInfos = 
				workRecordsInDB.stream()
				.map(WorkRecord::getWorkRecordInfo)
				.collect(Collectors.toList());
		
		List<TimeCard> timeCards = calculateTimeCard(sourceRecordInfos,workDate);
		
		SUT.finishCurrentDateWorkRecord(workDate);
		
		verify(timeCardRepository,times(1)).save(timeCardsToSaveCaptor.capture());
		List<TimeCard> timeCardsToBeSave = new LinkedList<>();
		timeCardsToSaveCaptor.getValue().forEach(e -> timeCardsToBeSave.add(e));
		assertEquals(timeCards,timeCardsToBeSave);
		verify(workRecordHistRepository,times(1)).save(workRecordHistToBeSave.capture());
		List<WorkRecordHist> listOfWorkRecordHistToBeSave = new LinkedList<>();
		workRecordHistToBeSave.getValue().forEach(e -> listOfWorkRecordHistToBeSave.add(e));
		assertEquals(sourceRecordInfos, listOfWorkRecordHistToBeSave);
		verify(workRecordRepository,times(1)).save(workRecordsToBeDelete.capture());
		List<WorkRecord> listOfRecordToBeDelete = workRecordsToBeDelete.getValue();
		assertEquals(workRecordsInDB, listOfRecordToBeDelete);
	}

	private List<WorkRecord> getTestWorkRecords(Date workDate) {
		List<WorkRecord> results = new LinkedList<>();
		results.add(buildRecordWhichEmployeHasOneRecord(workDate));
		results.addAll(buildRecordWhichEmployeHasTwoRecord(workDate));
		results.add(buildRecordWhichEmployeDoesNotFinishWork(workDate));
		return results;
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

	private List<TimeCard> calculateTimeCard(List<WorkRecordInfo> sourceRecordInfos,Date currentWorkDate) {
		Map<HourlyEmployee, List<WorkRecordInfo>> employeeInfoMap = sourceRecordInfos.stream()
				.collect(Collectors.groupingBy(WorkRecordInfo::getEmployee));
		return employeeInfoMap.entrySet().stream()
				.map(entry -> {
					List<WorkRecordInfo> recordInfos = entry.getValue();
					Date workTime = recordInfos.stream()
							.map(info -> {
								Date endTime = info.getEndTime()
									,startTime = info.getStartTime();
								if(endTime == null) {
									endTime = new Date(startTime.getTime());
									endTime.setHours(23);
									endTime.setMinutes(59);
									endTime.setSeconds(59);
								}
								return DateUtils.sub(endTime, startTime);
							})
							.collect(Collectors.reducing(new Date(0), (a,b) -> DateUtils.add(a, b)));
					return new TimeCard(
							entry.getKey()
							, currentWorkDate
							, workTime.getHours()
							);
				}).collect(Collectors.toList());
	}
	
	private HourlyEmployee buildSimpleHourlyEmployeeWithIdAndRate(int empID, double rate) {
		HourlyEmployee employee = new HourlyEmployee(empID);
		employee.setName("test_" + empID);
		employee.setAddress("testAddress_" + empID);
		employee.setHourlyRate(new BigDecimal(rate));
		return employee;
	}
}
