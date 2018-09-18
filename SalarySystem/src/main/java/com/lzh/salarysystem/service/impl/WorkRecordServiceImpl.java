package com.lzh.salarysystem.service.impl;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lzh.salarysystem.common.util.DateUtils;
import com.lzh.salarysystem.common.util.ValidatorHelper;
import com.lzh.salarysystem.entity.Employee;
import com.lzh.salarysystem.entity.HourlyEmployee;
import com.lzh.salarysystem.entity.TimeCard;
import com.lzh.salarysystem.entity.WorkRecord;
import com.lzh.salarysystem.entity.WorkRecordInfo;
import com.lzh.salarysystem.repository.EmployeeRepository;
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
		Date currentDateTime = new Date();
		newWorkRecordInfo.setStartTime(currentDateTime);
		newWorkRecord.setWorkRecordInfo(newWorkRecordInfo);
		return newWorkRecord;
	}

	@Override
	public void logWorkEnd(Integer empID) {
		Employee employee = employeeRepository.findOne(empID);
		validatorHelper.getValidator(ValidateEmployeeBeforeLogWorkEnd.class).validate(employee);
		
		WorkRecord currentWorkRecord = workRecordRepository.findCurrentWorkRecord(empID);
		currentWorkRecord.getWorkRecordInfo().setEndTime(new Date());
		workRecordRepository.save(currentWorkRecord);
	}

	@Override
	public void finishCurrentDateWorkRecord(Date workDate) {
		// TODO Auto-generated method stub
		
	}
	
	Collection<TimeCard> calculateTimeCardsFromWorkRecords(Collection<WorkRecord> sourceRecords,Date workDate){
		Map<HourlyEmployee, List<WorkRecordInfo>> employeeInfoMap = sourceRecords.stream()
				.map(WorkRecord::getWorkRecordInfo)
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
							, workDate
							, workTime.getHours()
							);
				}).collect(Collectors.toList());
	}
}
