package com.lzh.salarysystem.service;

import java.time.LocalDate;

import javax.transaction.Transactional;

import com.lzh.salarysystem.entity.WorkRecord;

public interface WorkRecordService {
	
	@Transactional
	void logWorkStart(Integer empID);
	
	@Transactional
	void logWorkEnd(Integer empID);
	
	@Transactional
	void finishCurrentDateWorkRecord(LocalDate workDate);

	WorkRecord findCurrentWorkRecord(Integer empID);
}
