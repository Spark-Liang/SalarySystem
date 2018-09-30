package com.lzh.salarysystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.lzh.salarysystem.entity.WorkRecord;

public interface WorkRecordRepository extends JpaRepository<WorkRecord, Long>,JpaSpecificationExecutor<WorkRecord>{
	
	WorkRecord findCurrentWorkRecord(Integer empID);
	
	WorkRecord findTopByInfo_employee_idOrderByInfo_startTimeDesc(Integer empID);
}
