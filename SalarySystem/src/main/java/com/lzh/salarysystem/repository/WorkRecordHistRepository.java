package com.lzh.salarysystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lzh.salarysystem.domain.entity.WorkRecordHist;

public interface WorkRecordHistRepository extends JpaRepository<WorkRecordHist, Long>{

}
