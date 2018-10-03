package com.lzh.salarysystem.service.impl;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lzh.salarysystem.common.util.ValidatorHelper;
import com.lzh.salarysystem.domain.entity.Employee;
import com.lzh.salarysystem.domain.entity.HourlyEmployee;
import com.lzh.salarysystem.domain.entity.TimeCard;
import com.lzh.salarysystem.domain.entity.WorkRecord;
import com.lzh.salarysystem.domain.entity.WorkRecordHist;
import com.lzh.salarysystem.domain.valueobject.WorkRecordInfo;
import com.lzh.salarysystem.repository.EmployeeRepository;
import com.lzh.salarysystem.repository.TimeCardRepository;
import com.lzh.salarysystem.repository.WorkRecordHistRepository;
import com.lzh.salarysystem.repository.WorkRecordRepository;
import com.lzh.salarysystem.service.WorkRecordService;
import com.lzh.salarysystem.service.validator.ValidateEmployeeBeforeLogWorkEnd;
import com.lzh.salarysystem.service.validator.ValidateEmployeeBeforeLogWorkStart;

@Service
public class WorkRecordServiceImpl implements WorkRecordService {

	@Autowired
	private WorkRecordRepository workRecordRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private TimeCardRepository timeCardRepository;
	
	@Autowired
	private WorkRecordHistRepository workRecordHistRepository;
	
	@Autowired
	private ValidatorHelper validatorHelper;
	
	@Override
	public void logWorkStart(Integer empID) {
		Employee employee = employeeRepository.findOne(empID);
		validatorHelper.getValidator(ValidateEmployeeBeforeLogWorkStart.class).validate(employee);
		
		WorkRecord newWorkRecord = createNewWorkRecord(employee);
		
		workRecordRepository.save(newWorkRecord);
	}

	private WorkRecord createNewWorkRecord(Employee employee) {
		WorkRecord newWorkRecord = new WorkRecord();
		WorkRecordInfo newWorkRecordInfo = new WorkRecordInfo();
		newWorkRecordInfo.setEmployee((HourlyEmployee) employee);
		LocalTime currentDateTime = LocalTime.now();
		newWorkRecordInfo.setStartTime(currentDateTime);
		newWorkRecord.setInfo(newWorkRecordInfo);
		return newWorkRecord;
	}

	@Override
	public void logWorkEnd(Integer empID) {
		Employee employee = employeeRepository.findOne(empID);
		validatorHelper.getValidator(ValidateEmployeeBeforeLogWorkEnd.class).validate(employee);
		
		WorkRecord currentWorkRecord = findCurrentWorkRecord(empID);
		currentWorkRecord.getInfo().setEndTime(LocalTime.now());
		workRecordRepository.save(currentWorkRecord);
	}

	@Override
	public void finishCurrentDateWorkRecord(LocalDate workDate) {
		List<WorkRecord> workRecordInDBs = workRecordRepository.findAll();
		
		Collection<TimeCard> timeCards = calculateTimeCardsFromWorkRecords(workRecordInDBs, workDate);
		timeCardRepository.save(timeCards);
		
		List<WorkRecordHist> workRecordHistToBeSave = 
				workRecordInDBs.stream()
				.map(record -> {
					WorkRecordHist histResult = new WorkRecordHist();
					histResult.setWorkDate(workDate);
					histResult.setInfo(record.getInfo());
					return histResult;
				}).collect(Collectors.toList());
		workRecordHistRepository.save(workRecordHistToBeSave);
		
		workRecordRepository.deleteAll();
	}
	
	Collection<TimeCard> calculateTimeCardsFromWorkRecords(Collection<WorkRecord> sourceRecords,LocalDate workDate){
		Map<HourlyEmployee, List<WorkRecordInfo>> employeeInfoMap = sourceRecords.stream()
				.map(WorkRecord::getInfo)
				.collect(Collectors.groupingBy(WorkRecordInfo::getEmployee));
		return employeeInfoMap.entrySet().stream()
				.map(entry -> {
					List<WorkRecordInfo> recordInfos = entry.getValue();
					Duration workTime = recordInfos.stream()
							.filter(info -> info.getEndTime() != null)
							.map(info -> {
								Duration duration = 
										Duration.between(info.getStartTime(),info.getEndTime());
								return duration;
							})
							.collect(Collectors.reducing(Duration.ZERO, (a,b) -> a.plus(b)));
					return new TimeCard(
							entry.getKey()
							, workDate
							, (int)workTime.toHours()
							);
				}).collect(Collectors.toList());
	}
	
	@Override
	public WorkRecord findCurrentWorkRecord(Integer empID) {
		return workRecordRepository.findCurrentWorkRecord(empID);
	}
}
