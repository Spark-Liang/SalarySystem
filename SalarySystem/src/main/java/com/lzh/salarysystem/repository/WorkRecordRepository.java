package com.lzh.salarysystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lzh.salarysystem.entity.WorkRecord;

public interface WorkRecordRepository extends JpaRepository<WorkRecord, Long>{
	
	@Query("select top 1 rec from WorkRecord rec where employee.id = ?1 order by rec.startTime desc")
	WorkRecord findCurrentWorkRecord(Integer empID);

}
